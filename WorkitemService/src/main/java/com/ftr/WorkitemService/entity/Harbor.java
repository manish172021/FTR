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
public class Harbor {

    @Column(name = "COUNTRY")
    private String country;

    @Id
    @Column(name = "AVAILABLE_HARBOR_LOCATIONS")
    private String availableHarborLocations;

}
