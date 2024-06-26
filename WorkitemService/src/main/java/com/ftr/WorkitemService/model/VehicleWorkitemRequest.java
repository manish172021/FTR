package com.ftr.WorkitemService.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VehicleWorkitemRequest {

    @NotNull(message = "{vehicle.vehicleNumber.must}")
    @Pattern(regexp = "(([A-Za-z]{2})[0-9]{4})", message = "{vehicle.vehicleNumber.invalid}")
    private String vehicleNumber;

}
