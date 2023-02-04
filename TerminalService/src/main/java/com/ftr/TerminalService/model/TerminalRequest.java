package com.ftr.TerminalService.model;

import com.ftr.TerminalService.validate.ValidCountry;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TerminalRequest {

    @NotNull(message = "{terminal.terminalName.must}")
    @Length(min = 3, max = 20, message = "{terminal.terminalName.invalid}")
    private String terminalName;

    @NotNull(message = "{terminal.country.must}")
    @ValidCountry
    private String country;

    @NotNull(message = "{terminal.itemType.must}")
    @Length(min = 4, max = 30,message = "{terminal.itemType.invalid}")
    private String itemType;

    @NotNull(message = "{terminal.terminalDescription.must}")
    @Length(max = 30, message = "{terminal.terminalDescriptionLength.invalid}")
    @Pattern(regexp = "T[0-9]{1,5}[-][a-zA-Z\\s]+$", message = "{terminal.terminalDescription.invalid}")
    private String terminalDescription;


    @NotNull(message = "{terminal.capacity.must}")
    @Max(value = 99999, message = "{terminal.capacity.invalid}")
    private Integer capacity;

    @NotNull(message = "{terminal.status.must}")
     @Pattern(regexp = "^(AVAILABLE|NOT AVAILABLE)$", message = "{terminal.status.invalid}")
    private String status;

    @NotNull(message = "{terminal.harborLocation.must}")
    @Length(min = 5, max = 30, message = "{terminal.harborLocation.invalid}")
    private String harborLocation;

    @NotNull(message = "{terminal.availableCapacity.must}")
    @Max(value = 99999, message = "{terminal.availableCapacity.invalid}")
    private Integer availableCapacity;

}
