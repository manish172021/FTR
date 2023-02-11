package com.ftr.WorkitemService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
public class VehicleWorkitemRequest {

    @NotNull(message = "{vehicle.vehicleNumber.must}")
    @Pattern(regexp = "(([A-Za-z]{2})[0-9]{4})", message = "{vehicle.vehicleNumber.invalid}")
    private String vehicleNumber;

}
