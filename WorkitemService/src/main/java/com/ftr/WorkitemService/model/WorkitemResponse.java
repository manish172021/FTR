package com.ftr.WorkitemService.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkitemResponse {

    private String workitemId;
    private Long userId;
    private String itemName;
    private String itemType;
    private String itemDescription;
    private String messageToRecipient;
    private String quantity;
    private String sourceCountry;
    private String destinationCountry;
    private String availableHarborLocation;
    private Date shippingDate;
    private Integer amount;

}
