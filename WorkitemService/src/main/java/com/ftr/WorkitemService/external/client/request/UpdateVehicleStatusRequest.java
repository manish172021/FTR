package com.ftr.WorkitemService.external.client.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateVehicleStatusRequest {
    @NotNull(message = "{vehicle.vehicleStatus.must}")
    @Pattern(regexp = "^(Active|Retired|InProgress)$", message = "{vehicle.vehicleStatus.invalid}")
    private String vehicleStatus;
}
