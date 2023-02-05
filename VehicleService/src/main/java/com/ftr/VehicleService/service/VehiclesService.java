package com.ftr.VehicleService.service;

import com.ftr.VehicleService.exception.VehicleException;
import com.ftr.VehicleService.model.UpdateVehicleStatusRequest;
import com.ftr.VehicleService.model.VehicleRequest;
import com.ftr.VehicleService.model.VehicleResponse;

import java.util.List;

public interface VehiclesService {


    VehicleResponse insertNewVehicle(VehicleRequest vehicleRequest) throws VehicleException;

    List<VehicleResponse> fetchAvailableVehicles() throws VehicleException;

    String updateVehicleStatus(String vehicleNumber,UpdateVehicleStatusRequest updateVehicleStatusRequest) throws VehicleException;

    List<VehicleResponse> fetchVehicleDetailsByVehicleName(String vehicleName) throws VehicleException;

    String removeVehicle(String vehicleNumber) throws VehicleException;

    VehicleResponse fetchVehicleDetailsByVehicleNumber(String vehicleNumber) throws VehicleException;
}
