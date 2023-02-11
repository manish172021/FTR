package com.ftr.WorkitemService.service;


import com.ftr.WorkitemService.exception.WorkitemException;
import com.ftr.WorkitemService.model.*;

import java.util.List;

public interface WorkitemService {

    WorkitemResponse createWorkitem(WorkitemRequest workitemRequest);

    List<String> fetchAvailableHarborLocations(String fromCountry) throws WorkitemException;

    String updateWorkItem(String workItemId, UpdateWorkitemRequest updateWorkitemRequest) throws WorkitemException;

    List<WorkitemResponse> fetchWorkItemDetails() throws WorkitemException;

    VehicleWorkitemResponse trackWorkItemByUser(Integer userId) throws WorkitemException;

    String allocateVehicle(String workItemId, VehicleWorkitemRequest vehicleWorkitemRequest) throws WorkitemException;

    String fetchWorkItemStatus(String workItemId) throws WorkitemException;

    String updateWorkItemStatus(String workItemId) throws WorkitemException;

    VehicleWorkitemResponse fetchVehicleDetailsByVehicleNumber(String vehicleNumber) throws WorkitemException;

    String assignTerminalforWorKitem(String workItemId) throws WorkitemException;
}
