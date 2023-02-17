package com.ftr.CloudGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {


    @GetMapping("/userServiceFallBack")
    public String userServiceFallback() {
        return "User Service is down!";
    }

    @GetMapping("/terminalServiceFallBack")
    public String terminalServiceFallback() {
        return "Terminal Service is down!";
    }

    @GetMapping("/vehicleServiceFallBack")
    public String vehicleServiceFallback() {
        return "Vehicle Service is down!";
    }

    @GetMapping("/workitemServiceFallBack")
    public String workitemServiceFallback() {
        return "Workitem Service is down!";
    }
}
