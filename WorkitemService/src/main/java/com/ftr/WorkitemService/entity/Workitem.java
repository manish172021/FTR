package com.ftr.WorkitemService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Workitem {

    @Id
    @Column(name = "WORKITEM_ID")
    @GenericGenerator(name = "workitem_id_generator", strategy = "com.ftr.WorkitemService.entity.WorkitemIdGenerator")
    @GeneratedValue(generator = "workitem_id_generator")
    private String workitemId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "ITEM_TYPE")
    private String itemType;

    @Column(name = "ITEM_DESCRIPTION")
    private String itemDescription;

    @Column(name = "MESSAGE_TO_RECIPIENT")
    private String messageToRecipient;

    @Column(name = "QUANTITY")
    private String quantity;

    @Column(name = "SOURCE_COUNTRY")
    private String sourceCountry;

    @Column(name = "DESTINATION_COUNTRY")
    private String destinationCountry;

    @Column(name = "AVAILABLE_HARBOUR_LOCATION")
    private String availableHarborLocation;

    @Column(name = "SHIPPING_DATE")
    private Date shippingDate;

    @Column(name = "AMOUNT")
    private Integer amount;

}
