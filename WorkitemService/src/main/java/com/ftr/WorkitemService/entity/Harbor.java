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

    @Id
    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "AVAILABLE_HARBOR_LOCATIONS")
    private String availableHarborLocations;

}
