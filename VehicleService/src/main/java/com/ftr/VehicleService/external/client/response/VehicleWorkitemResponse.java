package com.ftr.VehicleService.external.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleWorkitemResponse {

    private String vehicleNumber;
    private String workitemId;
    private String assignedWorkItemStatus;

}
