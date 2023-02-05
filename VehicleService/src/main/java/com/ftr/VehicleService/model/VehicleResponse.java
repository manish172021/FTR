package com.ftr.VehicleService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    private String vehicleNumber;
    private String vehicleName;
    private Double maxLiftingCapacity;
    private Date retireDate;
    private String vehicleStatus;
    private String harborLocation;
    private String country;
}
