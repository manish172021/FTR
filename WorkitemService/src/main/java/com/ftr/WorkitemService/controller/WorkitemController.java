package com.ftr.WorkitemService.controller;

import com.ftr.WorkitemService.exception.WorkitemException;
import com.ftr.WorkitemService.model.*;
import com.ftr.WorkitemService.service.WorkitemService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/ftr/workItems")
@Validated
public class WorkitemController {

    @Autowired
    private WorkitemService workitemService;

    @Autowired
    private Environment environment;

    @PostMapping
    public ResponseEntity<WorkitemResponse> createWorkItem(@RequestBody @Valid WorkitemRequest workitemRequest) throws WorkitemException {
        WorkitemResponse workitemResponse = workitemService.createWorkitem(workitemRequest);
        String successMessage = environment.getProperty("workitem.create.success") + workitemResponse.getWorkitemId();
        return new ResponseEntity<>(workitemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{fromCountry}")
    public ResponseEntity<List<String>> fetchAvailableHarborLocations(@PathVariable("fromCountry") String fromCountry) throws WorkitemException {
        List<String> availableHarborLocations = workitemService.fetchAvailableHarborLocations(fromCountry);
        String successMessage = environment.getProperty("workitem.foundByCountry.success") + fromCountry;
        return new ResponseEntity<>(availableHarborLocations, HttpStatus.OK);
    }

    @PutMapping("/{workItemId}")
    public ResponseEntity<String> updateWorkItem(@PathVariable("workItemId") String workItemId, @RequestBody @Valid UpdateWorkitemRequest updateWorkitemRequest) throws WorkitemException {
        String updatedMessage = workitemService.updateWorkItem(workItemId, updateWorkitemRequest);
        String successMessage = environment.getProperty("workitem.update.success") + workItemId + "- " + updatedMessage;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<WorkitemResponse>> fetchWorkItemDetails() throws WorkitemException {
        List<WorkitemResponse> workitemResponses = workitemService.fetchWorkItemDetails();
        String successMessage = environment.getProperty("workitem.found.success");
        return new ResponseEntity<>(workitemResponses, HttpStatus.OK);
    }

    @GetMapping("/managed-user/{userId}")
    public ResponseEntity<VehicleWorkitemResponse> trackWorkItemByUser(@PathVariable("userId") Integer userId) throws WorkitemException {
        VehicleWorkitemResponse vehicleWorkitemResponse = workitemService.trackWorkItemByUser(userId);
        String successMessage = environment.getProperty("workitem.foundByUser.success") + userId;
        return new ResponseEntity<>(vehicleWorkitemResponse, HttpStatus.OK);
    }


    @PostMapping("/managed-user/{workItemId}")
    public ResponseEntity<String> allocateVehicle(@PathVariable("workItemId") String workItemId, @RequestBody @Valid VehicleWorkitemRequest vehicleWorkitemRequest) throws WorkitemException {
        String allocatedVehiclewokId = workitemService.allocateVehicle(workItemId, vehicleWorkitemRequest);
        String successMessage = environment.getProperty("workitem.vehicle.allocated") + allocatedVehiclewokId;
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }


    @GetMapping("/managed-status/{workItemId}")
    public ResponseEntity<String> fetchWorkItemStatus(@PathVariable("workItemId") String workItemId) throws WorkitemException {
        String workItemStatus = workitemService.fetchWorkItemStatus(workItemId);
        String successMessage = environment.getProperty("workitem.workItemStatus.success") + workItemId;
        return new ResponseEntity<>(workItemStatus, HttpStatus.OK);
    }


    @PutMapping("/managed-update/{workItemId}")
    public ResponseEntity<String> updateWorkItemStatus(@PathVariable("workItemId") String workItemId) throws WorkitemException {
        String updatedWorkitemId = workitemService.updateWorkItemStatus(workItemId);
        String successMessage = environment.getProperty("workitem.updateWorkItemStatus.success") + updatedWorkitemId;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }


    @GetMapping("/managed-vehicle/{vehicleNumber}")
    public ResponseEntity<VehicleWorkitemResponse> fetchVehicleDetailsByVehicleNumber(@PathVariable("vehicleNumber") String vehicleNumber) throws WorkitemException {
        VehicleWorkitemResponse vehicleWorkitemResponse = workitemService.fetchVehicleDetailsByVehicleNumber(vehicleNumber);
        String successMessage = environment.getProperty("vehicle.found.success") + vehicleNumber;
        return new ResponseEntity<>(vehicleWorkitemResponse, HttpStatus.OK);
    }

    @PostMapping("/managed-terminal/{workItemId}")
    public ResponseEntity<String> assignTerminalforWorKitem(@PathVariable("workItemId") String workItemId) throws WorkitemException {
        String updatedMessage = workitemService.assignTerminalforWorKitem(workItemId);
        String successMessage = environment.getProperty("workitem.assignTerminalforWorKitem.success") + updatedMessage;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
}
