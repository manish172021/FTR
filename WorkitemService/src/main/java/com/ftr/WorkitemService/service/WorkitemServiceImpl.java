package com.ftr.WorkitemService.service;

import com.ftr.WorkitemService.entity.Harbor;
import com.ftr.WorkitemService.entity.VehicleWorkitem;
import com.ftr.WorkitemService.entity.Workitem;
import com.ftr.WorkitemService.entity.WorkitemTerminal;
import com.ftr.WorkitemService.exception.WorkitemException;
import com.ftr.WorkitemService.external.client.TerminalService;
import com.ftr.WorkitemService.external.client.request.UpdateVehicleStatusRequest;
import com.ftr.WorkitemService.external.client.response.TerminalResponse;
import com.ftr.WorkitemService.external.client.response.VehicleResponse;
import com.ftr.WorkitemService.external.client.VehicleService;
import com.ftr.WorkitemService.model.*;
import com.ftr.WorkitemService.repository.HarborRepository;
import com.ftr.WorkitemService.repository.VehicleWorkitemRepository;
import com.ftr.WorkitemService.repository.WorkitemRepository;
import com.ftr.WorkitemService.repository.WorkitemTerminalRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class WorkitemServiceImpl implements WorkitemService {

    @Autowired
    private HarborRepository harborRepository;
    @Autowired
    private WorkitemRepository workitemRepository;
    @Autowired
    private VehicleWorkitemRepository vehicleWorkitemRepository;
    @Autowired
    private WorkitemTerminalRepository workitemTerminalRepository;

    @Autowired
    private Environment environment;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private TerminalService terminalService;

    @Override
    public WorkitemResponse createWorkitem(WorkitemRequest workitemRequest) {
        log.info("Creating Workitem...");

        Map<String, Double> itemsPrice = new HashMap<>();
        itemsPrice.put("Computer Hardware", 15999.0);
        itemsPrice.put("Oil Container", 22.54);
        itemsPrice.put("Wood", 130000.0);
        itemsPrice.put("Motorcycles", 6336.0);
        itemsPrice.put("Electronics", 13490.0);

        Workitem workitem
                = Workitem.builder()
                .userId(workitemRequest.getUserId())
                .itemName(workitemRequest.getItemName())
                .itemType(workitemRequest.getItemType())
                .itemDescription(workitemRequest.getItemDescription())
                .messageToRecipient(workitemRequest.getMessageToRecipient())
                .quantity(workitemRequest.getQuantity())
                .sourceCountry(workitemRequest.getSourceCountry())
                .destinationCountry(workitemRequest.getDestinationCountry())
                .availableHarborLocation(workitemRequest.getAvailableHarborLocation())
                .shippingDate(workitemRequest.getShippingDate())
                .amount(itemsPrice.get(workitemRequest.getItemType()).intValue())
                .build();
        workitemRepository.save(workitem);

        WorkitemResponse workitemResponse = new WorkitemResponse();
        BeanUtils.copyProperties(workitem, workitemResponse);
        log.info("created workitem...");
        return workitemResponse;
    }

    @Override
    public List<String> fetchAvailableHarborLocations(String fromCountry) throws WorkitemException {
        log.info("fetching available Harbor Locations...");
        List<Harbor> harbors = harborRepository.findByCountry(fromCountry);
        if(harbors == null || harbors.isEmpty()) {
            throw new WorkitemException("harbor.notFound");
        }
        String availableHarborLocations = harbors.stream()
                .map(harbor -> harbor.getAvailableHarborLocations())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("fetched available Harbor Locations...");
        return Collections.singletonList(availableHarborLocations);
    }

    @Override
    public String updateWorkItem(String workItemId, UpdateWorkitemRequest updateWorkitemRequest) throws WorkitemException {
        log.info("updating wokitems...");
        String response = "";
        Workitem workitem = workitemRepository.findById(workItemId)
                .orElseThrow(() -> new WorkitemException("workitem.notFound"));

        if(updateWorkitemRequest != null && !updateWorkitemRequest.getAvailableHarborLocation().isBlank()) {
            workitem.setAvailableHarborLocation(updateWorkitemRequest.getAvailableHarborLocation());
            response += "Available Harbor Location,";
        }
        if(updateWorkitemRequest != null && Objects.nonNull(updateWorkitemRequest.getShippingDate())) {
            workitem.setShippingDate(updateWorkitemRequest.getShippingDate());
            response += "Shipping Date,";
        }
        workitemRepository.save(workitem);
        log.info("updated wokitems...");
        return response.substring(0, response.length()-2);
    }

    @Override
    public List<WorkitemResponse> fetchWorkItemDetails() throws WorkitemException {
        log.info("fetching all Workitem details...");
        List<Workitem> workitems = workitemRepository.findAll()
                .stream()
                .collect(Collectors.toList());
        if(workitems == null || workitems.isEmpty()) {
            throw new WorkitemException("workitem.noWorkitems");
        }

        List<WorkitemResponse> workitemsResponse = workitems
                .stream()
                .map(workitem -> {
                    WorkitemResponse workitemResponse = new WorkitemResponse();
                    BeanUtils.copyProperties(workitem, workitemResponse);
                    return workitemResponse;
                })
                .collect(Collectors.toList());

        log.info("fetched all Workitem details...");
        return workitemsResponse;
    }

    @Override
    public VehicleWorkitemResponse trackWorkItemByUser(Integer userId) throws WorkitemException {

        Optional<Workitem> workitemOptional = Optional.ofNullable(workitemRepository.findByUserId(userId.longValue()));
        Workitem workitem = workitemOptional.orElseThrow(() -> new WorkitemException("workitem.user.fail"));

        Optional<VehicleWorkitem> vehicleWorkitemOptional = Optional.ofNullable(vehicleWorkitemRepository.findByWorkitemId(workitem.getWorkitemId()));
        VehicleWorkitem vehicleWorkitem = vehicleWorkitemOptional.orElseThrow(() -> new WorkitemException("workitem.vehicle.fail"));

        VehicleWorkitemResponse vehicleWorkitemResponse = new VehicleWorkitemResponse();
        BeanUtils.copyProperties(vehicleWorkitem, vehicleWorkitemResponse);

        return vehicleWorkitemResponse;
    }


    @Override
    public String allocateVehicle(String workItemId, VehicleWorkitemRequest vehicleWorkitemRequest) throws WorkitemException {
        /* 1.Consume VehicleMS to identify the available location based on the workitem type
                and harbor location and fetch the first available vehicle and assign to the given workitem.
           2. Also validate while selecting vehicle it should in active state.
           3. Check this work item id is assigned with vehicle already, if yes throw appropriate error message. */

        Optional<Workitem> workitemOptional = workitemRepository.findById(workItemId);
        Workitem workitem = workitemOptional.orElseThrow(() -> new WorkitemException("workitem.notFound"));

        List<VehicleResponse> VehiclesResponse = vehicleService.fetchVehicleByHarbor(workitem.getAvailableHarborLocation()).getBody()
                    .stream()
                    .filter(vehicle -> vehicle.getVehicleStatus().equals("Active"))
                    .collect(Collectors.toList());

        VehicleWorkitem vehicleWorkItem = vehicleWorkitemRepository.findByWorkitemId(workItemId);
        if(!Objects.isNull(vehicleWorkItem)) {
            throw new WorkitemException("workitem.alreadyVehicle.assign");
        }

        VehicleWorkitem vehicleWorkitem
                = VehicleWorkitem.builder()
                .vehicleNumber(VehiclesResponse.get(0).getVehicleNumber())
                .workitemId(workItemId)
                .assignedWorkItemStatus("InProgress")
                .build();
        vehicleWorkitemRepository.save(vehicleWorkitem);

        UpdateVehicleStatusRequest updateVehicleStatusRequest = new UpdateVehicleStatusRequest();
        updateVehicleStatusRequest.setVehicleStatus("InProgress");
        vehicleService.updateVehicleStatus(VehiclesResponse.get(0).getVehicleNumber(), updateVehicleStatusRequest);

        return workItemId;
    }

    @Override
    public String fetchWorkItemStatus(String workItemId) throws WorkitemException {
        Optional<Workitem> workitemOptional = workitemRepository.findById(workItemId);
        Workitem workitem = workitemOptional.orElseThrow(() -> new WorkitemException("workitem.notFound"));
        Optional<VehicleWorkitem> vehicleWorkitemOptional = Optional.ofNullable(vehicleWorkitemRepository.findByWorkitemId(workitem.getWorkitemId()));
        VehicleWorkitem vehicleWorkitem = vehicleWorkitemOptional.orElseThrow(() -> new WorkitemException("workitem.status.notAssigned"));
        return vehicleWorkitem.getAssignedWorkItemStatus();
    }

    @Override
    public String updateWorkItemStatus(String workItemId) throws WorkitemException {

        /* 1. Update WorKItemId status as completed if shipping date and current date are same/shipping date is < current date.
        2. If WorkItemId status is completed then update Vehicle status as active .
        3. Update capacity of Terminal if WorkItem status is completed */

        Optional<Workitem> workitemOptional = workitemRepository.findById(workItemId);
        Workitem workitem = workitemOptional.orElseThrow(() -> new WorkitemException("workitem.notFound"));

        Optional<VehicleWorkitem> vehicleWorkitemOptional = Optional.ofNullable(vehicleWorkitemRepository.findByWorkitemId(workitem.getWorkitemId()));
        VehicleWorkitem vehicleWorkitem = vehicleWorkitemOptional.orElseThrow(() -> new WorkitemException("workitem.status.notAssigned"));


        Timestamp timestamp = (Timestamp) workitem.getShippingDate();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if(!timestamp.after(currentTimestamp)) {

            vehicleWorkitem.setAssignedWorkItemStatus("Completed");
            vehicleWorkitemRepository.saveAndFlush(vehicleWorkitem);

            UpdateVehicleStatusRequest updateVehicleStatusRequest = new UpdateVehicleStatusRequest();
            updateVehicleStatusRequest.setVehicleStatus("Active");
            String vehicleNumber = vehicleWorkitem.getVehicleNumber();
            vehicleService.updateVehicleStatus(vehicleNumber, updateVehicleStatusRequest);

            Optional<WorkitemTerminal> workitemTerminalOptional = workitemTerminalRepository.findById(workItemId);
            WorkitemTerminal workitemTerminal = workitemTerminalOptional.orElseThrow(() -> new WorkitemException("workitem.noTerminal"));

            String terminalId = workitemTerminal.getTerminalId();
            Integer quantity = Integer.valueOf(workitem.getQuantity());
            String successUpdateTerminal = terminalService.updateTerminal(terminalId, quantity).getBody();
        }
        return workItemId;
    }

    @Override
    public VehicleWorkitemResponse fetchVehicleDetailsByVehicleNumber(String vehicleNumber) throws WorkitemException {
        log.info("fetching Vehicle Details by vehicle number...");
        VehicleWorkitem vehicleWorkItem =  vehicleWorkitemRepository.findById(vehicleNumber).
                orElseThrow(() -> new WorkitemException("vehicle.workitem.fail"));
        VehicleWorkitemResponse vehicleWorkitemResponse = new VehicleWorkitemResponse();
        BeanUtils.copyProperties(vehicleWorkItem, vehicleWorkitemResponse);
        log.info("fetched Vehicle Details by vehicle number...");
        return vehicleWorkitemResponse;
    }

    @Override
    public String assignTerminalforWorKitem(String workItemId) throws WorkitemException {
        /* 1. Fetch TerminalID based on ItemType of this WorkItem(call to TerminalService)
           and insert in ftr_workItem_Terminal table.
           Update the Terminal table 's AvailableCapacity field by reducing the
           availableCapacity=avaliableCapacity-quantity (call to TerminalService) */


        Optional<Workitem> workitemOptional = workitemRepository.findById(workItemId);
        Workitem workitem = workitemOptional.orElseThrow(() -> new WorkitemException("workitem.notFound"));

        String itemType = workitem.getItemType();
        String quantity = workitem.getQuantity();
        int integerQuantity = Integer.parseInt(quantity.replaceAll("\\D.*", ""));

        List<TerminalResponse> terminalsResponse = terminalService.fetchTerminalByItemType(itemType).getBody()
                .stream()
                .filter(terminal -> terminal.getAvailableCapacity() > integerQuantity && terminal.getStatus().equals("AVAILABLE"))
                .collect(Collectors.toList());

        if(Objects.isNull(terminalsResponse)) {
            throw new WorkitemException("terminal.notAvailable");
        }


        WorkitemTerminal workitemTerminal
                = WorkitemTerminal.builder()
                .workitemId(workitem.getWorkitemId())
                .terminalId(terminalsResponse.get(0).getTerminalId())
                .build();
        workitemTerminalRepository.saveAndFlush(workitemTerminal);

        terminalService.updateTerminal(terminalsResponse.get(0).getTerminalId(), integerQuantity);

        return terminalsResponse.get(0).getTerminalId();

    }
}
