package com.ftr.WorkitemService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleWorkItem {

    @Id
    @Column(name = "VEHILCE_NUMBER")
    private String vehicleNumber;

    @Column(name = "WORKITEM_ID")
    private String workitemId;

    @Column(name = "ASSIGNED_WORK_ITEM_STATUS")
    private String assignedWorkItemStatus;

}
