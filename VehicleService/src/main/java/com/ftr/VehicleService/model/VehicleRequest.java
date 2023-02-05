package com.ftr.VehicleService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ftr.VehicleService.validate.OnlyInteger;
import com.ftr.VehicleService.validate.ValidCountry;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Date;

@Data
public class VehicleRequest {


    @NotNull(message = "{vehicle.vehicleNumber.must}")
    @Pattern(regexp = "(([A-Za-z]{2})[0-9]{4})", message = "{vehicle.vehicleNumber.invalid}")
    private String vehicleNumber;

    @NotNull(message = "{vehicle.vehicleName.must}")
    @Size(max = 30, message = "{vehicle.vehicleName.invalidLength}")
    @Pattern(regexp = "(?i)(Tower crane|FirePlace Crane|Double treadwheel Crane|Crawler Crane|Aerial Crane|Deck Crane)", message = "{vehicle.vehicleName.invalid}")
    private String vehicleName;

    @NotNull(message = "{vehicle.maxLiftingCapacity.must}")
    @OnlyInteger(message = "{vehicle.maxLiftingCapacity.invalid}")
    private Double maxLiftingCapacity;

    @NotNull(message = "{vehicle.retireDate.must}")
    @JsonFormat(pattern="yyyy-mm-dd")
    private Date retireDate;

    @NotNull(message = "{vehicle.vehicleStatus.must}")
    @Pattern(regexp = "^(Active|Retired|InProgress)$", message = "{vehicle.vehicleStatus.invalid}")
    private String vehicleStatus;

    @NotNull(message = "{vehicle.harborLocation.must}")
    @Size(min = 5, max = 25, message = "{vehicle.harbourLocation.lengthInvalid}")
    @Pattern(regexp = "^[A-Za-z][A-za-z ]*$", message = "{vehicle.harbourLocation.invalid}")
    private String harborLocation;

    @NotNull(message = "{vehicle.country.must}")
    @ValidCountry(message = "{vehicle.country.invalid}")
    private String country;

}
