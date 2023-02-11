package com.ftr.WorkitemService.service;


import com.ftr.WorkitemService.entity.Harbor;
import com.ftr.WorkitemService.entity.VehicleWorkItem;
import com.ftr.WorkitemService.entity.WorkItemTerminal;
import com.ftr.WorkitemService.entity.Workitem;
import com.ftr.WorkitemService.exception.WorkitemException;
import com.ftr.WorkitemService.model.*;
import com.ftr.WorkitemService.repository.HarborRepository;
import com.ftr.WorkitemService.repository.VehicleWorkitemRepository;
import com.ftr.WorkitemService.repository.WorkitemRepository;
import com.ftr.WorkitemService.repository.WorkitemTerminalRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
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

    // TODO:1
    @Override
    public VehicleWorkitemResponse trackWorkItemByUser(Integer userId) throws WorkitemException {

//        Optional<Workitem> workitemOptional = Optional.ofNullable(workitemRepository.finByUserId(Long.valueOf(userId)));
//        Workitem workitem = workitemOptional.orElseThrow(() -> new WorkitemException("workitem.user.fail"));
//
//        Optional<VehicleWorkItem> vehicleWorkItemOptional = Optional.ofNullable(vehicleWorkitemRepository.findByWorkitemId(workitem.getWorkitemId()));
//        VehicleWorkItem vehicleWorkItem = vehicleWorkItemOptional.orElseThrow(() -> new WorkitemException("workitem.vehicle.fail"));
//
//        VehicleWorkitemResponse vehicleWorkitemResponse = new VehicleWorkitemResponse();
//        BeanUtils.copyProperties(vehicleWorkItem, vehicleWorkitemResponse);
//
//        return vehicleWorkitemResponse;
        return null;
    }
    // TODO:2
    @Override
    public String allocateVehicle(String workItemId, VehicleWorkitemRequest vehicleWorkitemRequest) throws WorkitemException {
//        // 1.Consume VehicleMS to identify the available location based on the workitem type
//        // and harbor location and fetch the first available vehicle and assign to the given workitem.
//        // 2. Also validate while selecting vehicle it should in active state.
//        // 3. Check this work item id is assigned with vehicle already, if yes throw appropriate error message.
//
//        Optional<Workitem> workitemOptional = workitemRepository.findById(workItemId);
//        Workitem workitem = workitemOptional.orElseThrow(() -> new WorkitemException("workitem.notFound"));
//
//        // TODO HANDLE EXCEPTION AND CONNECT MS
//        List<VehicleResponse> VehiclesResponse = vehicleFeign.fetchVehicleByHarbor(workitem.getAvailableHarborLocation())
//                .stream()
//                .filer(vehicleStatus -> vehicleStatus.equals("Active"))
//                .collect(Collectors.toList());
//
//        VehicleWorkItem vehicleWorkItem = vehicleWorkitemRepository.findByWorkitemId(workItemId);
//        if(!Objects.isNull(vehicleWorkItem)) {
//            throw new WorkitemException("workitem.alreadyVehicle.assign");
//        }
//        VehicleWorkItem vehicleWorkItem
//                = VehicleWorkItem.builder()
//                .vehicleNumber(VehiclesResponse.get(0).getVehicleNumber())
//                .workitemId(workItemId)
//                .assignedWorkItemStatus("InProgress")
//                .build();
//        vehicleWorkitemRepository.save(vehicleWorkItem);
//        UpdateVehicleStatusRequest updateVehicleStatusRequest = UpdateVehicleStatusRequest.builder().vehicleStatus("InProgress").build();
//        vehicleFeign.updateVehicleStatus(VehiclesResponse.get(0).getVehicleNumber(), updateVehicleStatusRequest);
//        return workItemId;
        return null;
    }

    // TODO:3
    @Override
    public String fetchWorkItemStatus(String workItemId) throws WorkitemException {
//        Optional<Workitem> workitemOptional = workitemRepository.findById(workItemId);
//        Workitem workitem = workitemOptional.orElseThrow(() -> new WorkitemException("workitem.notFound"));
//        Optional<VehicleWorkItem> vehicleWorkItemOptional = Optional.ofNullable(vehicleWorkitemRepository.findByWorkitemId(workitem.getWorkitemId()));
//        VehicleWorkItem vehicleWorkItem = vehicleWorkItemOptional.orElseThrow(() -> new WorkitemException("workitem.status.notAssigned"));
//        return vehicleWorkItem.getAssignedWorkItemStatus();
        return null;
    }

    // TODO:4
    @Override
    public String updateWorkItemStatus(String workItemId) throws WorkitemException {

////        Update WorKItemId status as completed if shipping date and current date are same/shipping date is < current date.
////        If WorkItemId status is completed then update Vehicle status as active .
////        Update capacity of Terminal if WorkItem status is completed
//
//        Optional<Workitem> workitemOptional = workitemRepository.findById(workItemId);
//        Workitem workitem = workitemOptional.orElseThrow(() -> new WorkitemException("workitem.notFound"));
//        Optional<VehicleWorkItem> vehicleWorkItemOptional = Optional.ofNullable(vehicleWorkitemRepository.findByWorkitemId(workitem.getWorkitemId()));
//        VehicleWorkItem vehicleWorkItem = vehicleWorkItemOptional.orElseThrow(() -> new WorkitemException("workitem.status.notAssigned"));
//
//        if(!workitem.getShippingDate().after(LocalDate.now())) {
//            vehicleWorkItem.setAssignedWorkItemStatus("Completed");
//            vehicleWorkitemRepository.saveAndFlush(vehicleWorkItem);
//
//            UpdateVehicleStatusRequest updateVehicleStatusRequest = UpdateVehicleStatusRequest.builder().vehicleStatus("Active").build();
//            vehicleFeign.updateVehicleStatus(VehiclesResponse.get(0).getVehicleNumber(), updateVehicleStatusRequest);
//
//            Optional<WorkItemTerminal> workItemTerminalOptional = workitemTerminalRepository.findById(workItemId);
//            WorkItemTerminal workItemTerminal = workItemTerminalOptional.orElseThrow(() -> new WorkitemException("workitem.noTerminal"));
//            // TODO according to the quantity validation parse it, right now considing string of numbers
//            terminalFeign.updateTerminal(workItemTerminal.getTerminalId(), Integer.parseInt(workitem.getQuantity()));
//        }

        return workItemId;
    }

    @Override
    public VehicleWorkitemResponse fetchVehicleDetailsByVehicleNumber(String vehicleNumber) throws WorkitemException {
        log.info("fetching Vehicle Details by vehicle number...");
        VehicleWorkItem vehicleWorkItem =  vehicleWorkitemRepository.findById(vehicleNumber).
                orElseThrow(() -> new WorkitemException("workitem.vehicle.fail"));
        VehicleWorkitemResponse vehicleWorkitemResponse = new VehicleWorkitemResponse();
        BeanUtils.copyProperties(vehicleWorkItem, vehicleWorkitemResponse);
        log.info("fetched Vehicle Details by vehicle number...");
        return vehicleWorkitemResponse;
    }

    // TODO:5
    @Override
    public String assignTerminalforWorKitem(String workItemId) throws WorkitemException {
//        // Fetch TerminalID based on ItemType of this WorkItem(call to TerminalService)
//        // and insert in ftr_workItem_Terminal table.
//        // Update the Terminal table 's AvailableCapacity field by reducing the
//        // availableCapacity=avaliableCapacity-quantity (call to TerminalService)
//
//        Optional<Workitem> workitemOptional = workitemRepository.findById(workItemId);
//        Workitem workitem = workitemOptional.orElseThrow(() -> new WorkitemException("workitem.notFound"));
//
//        List<TerminalResponse> terminalsResponse = terminalFeign.fetchTerminalByItemType(workitem.getItemType())
//                .stream()
//                .filter(terminal -> terminal.getAvailableCapacity > workitem.getQuantity() && terminal.getStatus().equals("Available"));
//        if(!Objects.isNull(terminalsResponse)) {
//            throw new WorkitemException("terminal.notAvailable");
//        }
//
//
//        WorkItemTerminal workItemTerminal
//                = WorkItemTerminal.builder()
//                .workitemId(workitem.getWorkitemId())
//                .terminalId(terminalsResponse.get(0).getTerminaId())
//                .build();
//        workitemTerminalRepository.saveAndFlush(workItemTerminal);
//        terminalFeign.updateTerminal(terminalsResponse.get(0).getTerminaId(), workitem.getQuantity());
//
//        return terminalsResponse.get(0).getTerminaId();
        return null;

    }
}
