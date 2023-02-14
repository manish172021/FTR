package com.ftr.WorkitemService.external.client;

import com.ftr.WorkitemService.external.client.request.UpdateVehicleStatusRequest;
import com.ftr.WorkitemService.external.client.response.VehicleResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "VEHICLE-SERVICE/ftr/vehicles")
public interface VehicleService {

    @GetMapping("/harbor/{harborLocation}")
    public  ResponseEntity<List<VehicleResponse>> fetchVehicleByHarbor(@PathVariable("harborLocation") String harborLocation);

    @PutMapping("/{vehicleNumber}")
    public ResponseEntity<String> updateVehicleStatus(@PathVariable("vehicleNumber") String vehicleNumber, @RequestBody @Valid UpdateVehicleStatusRequest updateVehicleStatusRequest);

}
