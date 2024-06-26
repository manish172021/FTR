package com.ftr.VehicleService.controller;

import com.ftr.VehicleService.exception.VehicleException;
import com.ftr.VehicleService.model.UpdateVehicleStatusRequest;
import com.ftr.VehicleService.model.VehicleRequest;
import com.ftr.VehicleService.model.VehicleResponse;
import com.ftr.VehicleService.service.VehiclesService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/ftr/vehicles")
@Validated
public class VehiclesController {
    @Autowired
    private VehiclesService vehiclesService;

    @Autowired
    private Environment environment;

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @PostMapping
    public ResponseEntity<VehicleResponse> insertNewVehicle(@RequestBody @Valid VehicleRequest vehicleRequest) throws VehicleException {
        VehicleResponse vehicleResponse = vehiclesService.insertNewVehicle(vehicleRequest);
        String successMessage = environment.getProperty("vehicle.create.success") + vehicleResponse.getVehicleNumber();
        return new ResponseEntity<>(vehicleResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> fetchAvailableVehicles() throws VehicleException {
        List<VehicleResponse> vehicleResponses = vehiclesService.fetchAvailableVehicles();
        String successMessage = environment.getProperty("vehicles.fetch.success");
        return new ResponseEntity<>(vehicleResponses, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('internal') || hasAuthority('ROLE_Admin')")
    @PutMapping("/{vehicleNumber}")
    public ResponseEntity<String> updateVehicleStatus(@PathVariable("vehicleNumber") String vehicleNumber, @RequestBody @Valid UpdateVehicleStatusRequest updateVehicleStatusRequest ) throws VehicleException {
        String updatedMessage = vehiclesService.updateVehicleStatus(vehicleNumber, updateVehicleStatusRequest);
        String successMessage = environment.getProperty("vehicle.update.success") + updateVehicleStatusRequest.getVehicleStatus();
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @GetMapping("/managed-name/{vehicleName}")
    public ResponseEntity<List<VehicleResponse>> fetchVehicleDetailsByVehicleName(@PathVariable("vehicleName") String vehicleName) throws VehicleException {
        List<VehicleResponse> vehicleResponses = vehiclesService.fetchVehicleDetailsByVehicleName(vehicleName);
        String successMessage = environment.getProperty("vehicles.fetchByName.success") + vehicleName;
        return new ResponseEntity<>(vehicleResponses, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @DeleteMapping("/{vehicleNumber}")
    public ResponseEntity<String> removeTerminal(@PathVariable("vehicleNumber") String vehicleNumber) throws VehicleException {
        String deletedVehicleNumber = vehiclesService.removeVehicle(vehicleNumber);
        String successMessage = environment.getProperty("vehicle.delete.success") + deletedVehicleNumber;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @GetMapping("/managed-number/{vehicleNumber}")
    public ResponseEntity<VehicleResponse> fetchVehicleDetailsByVehicleNumber(@PathVariable("vehicleNumber") String vehicleNumber) throws VehicleException {
        VehicleResponse vehicleResponse = vehiclesService.fetchVehicleDetailsByVehicleNumber(vehicleNumber);
        String successMessage = environment.getProperty("vehicle.found.success");
        return new ResponseEntity<>(vehicleResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('internal')")
    @GetMapping("/harbor/{harborLocation}")
    public ResponseEntity<List<VehicleResponse>> fetchVehicleByHarbor(@PathVariable("harborLocation") String harborLocation) throws VehicleException {
        List<VehicleResponse> vehicleResponses = vehiclesService.fetchVehicleByHarbor(harborLocation);
        String successMessage = environment.getProperty("vehicles.fetch.success");
        return new ResponseEntity<>(vehicleResponses, HttpStatus.OK);
    }
}
