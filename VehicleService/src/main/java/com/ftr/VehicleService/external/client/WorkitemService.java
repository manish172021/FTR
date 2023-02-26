package com.ftr.VehicleService.external.client;

import com.ftr.VehicleService.exception.CustomFeignException;
import com.ftr.VehicleService.external.client.response.VehicleWorkitemResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

//@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "WORKITEM-SERVICE/ftr/workItems")
public interface WorkitemService {

    @GetMapping("/managed-vehicle/{vehicleNumber}")
    public ResponseEntity<VehicleWorkitemResponse> fetchVehicleDetailsByVehicleNumber(@PathVariable("vehicleNumber") String vehicleNumber);

    default ResponseEntity<Long> fallback(Exception e) {
        LocalDateTime timeStamp = LocalDateTime.now();
        String errorCode = String.valueOf(HttpStatus.SERVICE_UNAVAILABLE);
        String errorMessage = "Workitem Service is not available!";
        int status = HttpStatus.SERVICE_UNAVAILABLE.value();
        throw new CustomFeignException(timeStamp, errorCode, errorMessage, status);
    }
}