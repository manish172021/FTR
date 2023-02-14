package com.ftr.VehicleService.external.client;

import com.ftr.VehicleService.external.client.response.VehicleWorkitemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "WORKITEM-SERVICE/ftr/workItems")
public interface WorkitemService {

    @GetMapping("/managed-vehicle/{vehicleNumber}")
    public ResponseEntity<VehicleWorkitemResponse> fetchVehicleDetailsByVehicleNumber(@PathVariable("vehicleNumber") String vehicleNumber);
}

