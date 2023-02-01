package com.ftr.UserService.model;

import com.ftr.UserService.validate.ValidCountry;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class UserProfileUpdateRequest {

    @Size(min = 1, max = 20, message = "{user.firstName.invalid}")
    // @NotNull(message = "{user.firstName.must}")
    private String firstName;

    @Size(max = 20, message = "{user.lastName.invalid}")
    // @NotNull(message = "{user.lastName.must}")
    private String lastName;

    @Email(message = "{user.email.invalid}")
    // @NotNull(message = "{user.email.must}")
    private String emailId;

    // @NotNull(message = "{user.phone.must}")
    @Range(max = 9999999999L, min = 1000000000L, message = "{user.phone.invalid}")
    private Long mobileNumber;

    // @NotNull(message = "{user.password.must}")
    @Size(min = 7, max = 15, message = "{user.password.invalidsize}")
    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>])(?=\\S{7,15}$).*$", message = "{user.password.invalid}")
    private String password;

    // @NotNull(message = "{user.nationality.must}")
    @ValidCountry
    private String nationality;

    @Size(max = 12, min = 7, message = "{user.passportNumber.invalid}")
    // @NotNull(message = "{user.passportNumber.invalid}")
    private String passportNumber;

    @Size(max = 200, message = "{user.permanentAddress.invalid}")
    // @NotNull(message = "{user.permanentAddress.invalid}")
    private String permanentAddress;

    @Size(max = 200, message = "{user.officeAddress.invalid}")
    // @NotNull(message = "{user.officeAddress.invalid}")
    private String officeAddress;

    @Max(value = 999999999999L, message = "{user.personalIdentificationNumber.invalid}")
    @Min(value = 100000000000L, message = "{user.personalIdentificationNumber.invalid}")
    // @NotNull(message = "{user.personalIdentificationNumber.invalid}")
    private Long personalIdentificationNumber;

}
