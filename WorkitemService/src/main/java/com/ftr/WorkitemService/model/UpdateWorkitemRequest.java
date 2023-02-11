package com.ftr.WorkitemService.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
public class UpdateWorkitemRequest {

    @Length(min = 5, max = 25, message = "{workitem.availableHarborLocation.invalid}")
    private String availableHarborLocation;

    @JsonFormat(pattern="yyyy-mm-dd")
    private Date shippingDate;

}
