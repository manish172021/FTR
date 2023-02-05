package com.ftr.VehicleService.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {

    @Id
    @Column(name = "VEHICLE_NUMBER")
    private String vehicleNumber;

    @Column(name = "VEHICLE_NAME")
    private String vehicleName;

    @Column(name = "MAX_LIFTING_CAPACITY")
    private Double maxLiftingCapacity;

    @Column(name = "RETIRE_DATE")
    private Date retireDate;

    @Column(name = "VEHICLE_STATUS")
    private String vehicleStatus;

    @Column(name = "HARBOR_LOCATION")
    private String harborLocation;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "DELETED_STATUS")
    private String deleted_status;

    @PrePersist
    public void prePersist() {
        this.deleted_status = "Active";
    }
}
