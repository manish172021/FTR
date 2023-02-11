package com.ftr.WorkitemService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ftr.WorkitemService.validate.ValidCountry;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
public class WorkitemRequest {

    @NotNull(message = "{workitem.userId.must}")
    @Max(value = 99999, message = "{terminal.capacity.invalid}")
    private Long userId;

    @NotNull(message = "{workitem.itemName.must}")
    @Length(min = 3, max = 20, message = "{workitem.itemName.invalid}")
    private String itemName;

    @NotNull(message = "{workitem.itemType.must}")
    @Length(min = 4, max = 25, message = "{workitem.itemType.invalid}")
    private String itemType;

    @NotNull(message = "{workitem.itemDescription.must}")
    @Length(min = 10, max = 45, message = "{workitem.itemDescription.invalid}")
    private String itemDescription;

    @NotNull(message = "{workitem.messageToRecipient.must}")
    @Length(min = 10, max = 50, message = "{workitem.messageToRecipient.invalid}")
    private String messageToRecipient;

    @NotNull(message = "{workitem.quantity.must}")
    @Pattern(regexp = "^\\d+(?:[a-zA-Z]{2}|[a-zA-Z]{3})?$", message = "{workitem.quantity.invalid}")
    private String quantity;

    @NotNull(message = "{workitem.sourceCountry.must}")
    @ValidCountry(message = "{workitem.sourceCountry.invalid}")
    private String sourceCountry;

    @NotNull(message = "{workitem.destinationCountry.must}")
    @ValidCountry(message = "{workitem.destinationCountry.invalid}")
    private String destinationCountry;

    @NotNull(message = "{workitem.availableHarborLocation.must}")
    @Length(min = 5, max = 25, message = "{workitem.availableHarborLocation.invalid}")
    private String availableHarborLocation;

    @NotNull(message = "{workitem.shippingDate.must}")
    @JsonFormat(pattern="yyyy-mm-dd")
    private Date shippingDate;

}
