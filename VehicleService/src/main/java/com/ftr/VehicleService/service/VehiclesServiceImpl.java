package com.ftr.VehicleService.service;

import com.ftr.VehicleService.entity.Vehicle;
import com.ftr.VehicleService.exception.VehicleException;
import com.ftr.VehicleService.model.UpdateVehicleStatusRequest;
import com.ftr.VehicleService.model.VehicleRequest;
import com.ftr.VehicleService.model.VehicleResponse;
import com.ftr.VehicleService.repository.VehiclesRepository;
import jakarta.validation.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class VehiclesServiceImpl implements VehiclesService {

    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Autowired
    private Environment environment;


    @Override
    public VehicleResponse insertNewVehicle(VehicleRequest vehicleRequest) throws VehicleException {
        log.info("Inserting vehicle...");
        Optional<Vehicle> vehicleOptional = vehiclesRepository.findById(vehicleRequest.getVehicleNumber());
        if(vehicleOptional.isPresent() && !vehicleOptional.get().getDeleted_status().equals("DELETED")) {
            throw new VehicleException("vehicle.alreadyExists");
        }

        Vehicle vehicle
                = Vehicle.builder()
                .vehicleNumber(vehicleRequest.getVehicleNumber())
                .vehicleName(vehicleRequest.getVehicleName())
                .maxLiftingCapacity(vehicleRequest.getMaxLiftingCapacity())
                .retireDate(vehicleRequest.getRetireDate())
                .vehicleStatus(vehicleRequest.getVehicleStatus())
                .harborLocation(vehicleRequest.getHarborLocation())
                .country(vehicleRequest.getCountry())
                .build();
        vehiclesRepository.save(vehicle);

        VehicleResponse vehicleResponse = new VehicleResponse();
        BeanUtils.copyProperties(vehicle, vehicleResponse);
        log.info("vehicle inserted...");
        return vehicleResponse;
    }

    @Override
    public List<VehicleResponse> fetchAvailableVehicles() throws VehicleException {
        log.info("fetching all avaialable vehicles...");
        List<Vehicle> vehicles = vehiclesRepository.findAll()
                .stream()
                .filter(vehicle -> !vehicle.getDeleted_status().equals("DELETED"))
                .collect(Collectors.toList());
        if(vehicles == null) {
            throw new VehicleException("vehicles.empty");
        }

        List<VehicleResponse> vehicleResponses = vehicles
                .stream()
                .map(vehicle -> {
                    VehicleResponse vehicleResponse = new VehicleResponse();
                    BeanUtils.copyProperties(vehicle, vehicleResponse);
                    return vehicleResponse;
                })
                .collect(Collectors.toList());

        log.info("vehicles fetched...");
        return vehicleResponses;
    }

    @Override
    public String updateVehicleStatus(String vehicleNumber, UpdateVehicleStatusRequest updateVehicleStatusRequest) throws VehicleException {
        Vehicle vehicle = vehiclesRepository.findById(vehicleNumber)
                .filter(deleteVehicle -> !deleteVehicle.getDeleted_status().equals("DELETED"))
                .orElseThrow(() -> new ValidationException("vehicle.notFound"));

        if (updateVehicleStatusRequest.getVehicleStatus().equals(vehicle.getVehicleStatus())) {
            throw new VehicleException("vehicle.update.alreadyExists");
        } else {
            vehicle.setVehicleStatus(updateVehicleStatusRequest.getVehicleStatus());
        }
        vehiclesRepository.saveAndFlush(vehicle);
        log.info("vehicle status updated...");
        return vehicleNumber;
    }

    @Override
    public List<VehicleResponse> fetchVehicleDetailsByVehicleName(String vehicleName) throws VehicleException {
        log.info("fetching vehicles by name...");
        List<Vehicle> vehicles = vehiclesRepository.findByVehicleName(vehicleName)
                .stream()
                .filter(terminal -> !terminal.getDeleted_status().equals("DELETED"))
                .collect(Collectors.toList());
        if (vehicles.isEmpty()) {
            throw new VehicleException("vehicle.nameType.notFound");
        }
        List<VehicleResponse> vehicleResponses = vehicles
                .stream()
                .map(vehicle -> {
                    VehicleResponse vehicleResponse = new VehicleResponse();
                    BeanUtils.copyProperties(vehicle, vehicleResponse);
                    return vehicleResponse;
                })
                .collect(Collectors.toList());

        log.info("fetched vehicles by name...");
        return vehicleResponses;
    }

    @Override
    public String removeVehicle(String vehicleNumber) throws VehicleException {
        log.info("removing vehicle...");
        Vehicle vehicle = vehiclesRepository.findById(vehicleNumber)
                .orElseThrow(() -> new ValidationException("vehicle.notFound"));
        if(vehicle.getDeleted_status().equals("DELETED")) {
            throw new VehicleException("vehicle.already.deleted");
        }
        vehicle.setDeleted_status("DELETED");
        vehiclesRepository.saveAndFlush(vehicle);
        log.info("removed vehicle...");
        return vehicleNumber;
    }

    @Override
    public VehicleResponse fetchVehicleDetailsByVehicleNumber(String vehicleNumber) throws VehicleException {
        log.info("fetching vechile by number...");
        Vehicle vehicle = vehiclesRepository.findById(vehicleNumber)
                .filter(deletedVehicle -> !deletedVehicle.getDeleted_status().equals("DELETED"))
                .orElseThrow(() -> new VehicleException("vehicle.notFound"));

        VehicleResponse vehicleResponse = new VehicleResponse();
        BeanUtils.copyProperties(vehicle, vehicleResponse);
        log.info("fetched vechile by number fetched...");
        return vehicleResponse;

    }
}
