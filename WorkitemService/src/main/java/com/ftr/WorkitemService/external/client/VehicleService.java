package com.ftr.WorkitemService.external.client;

import com.ftr.WorkitemService.exception.CustomFeignException;
import com.ftr.WorkitemService.external.client.request.UpdateVehicleStatusRequest;
import com.ftr.WorkitemService.external.client.response.VehicleResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "VEHICLE-SERVICE/ftr/vehicles")
public interface VehicleService {

    @GetMapping("/harbor/{harborLocation}")
    public  ResponseEntity<List<VehicleResponse>> fetchVehicleByHarbor(@PathVariable("harborLocation") String harborLocation);

    @PutMapping("/{vehicleNumber}")
    public ResponseEntity<String> updateVehicleStatus(@PathVariable("vehicleNumber") String vehicleNumber, @RequestBody @Valid UpdateVehicleStatusRequest updateVehicleStatusRequest);

    default ResponseEntity<Long> fallback(Exception e) {
        LocalDateTime timeStamp = LocalDateTime.now();
        String errorCode = String.valueOf(HttpStatus.SERVICE_UNAVAILABLE);
        String errorMessage = "Vehicle Service is not available!";
        int status = HttpStatus.SERVICE_UNAVAILABLE.value();
        throw new CustomFeignException(timeStamp, errorCode, errorMessage, status);
    }
}
