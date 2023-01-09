package com.ftr.UserService.model;


import lombok.Data;

@Data
public class UserProfileRequest {

    private int userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private Long mobileNumber;
    private String password;
    private String nationality;
    private String passportNumber;
    private String permanentAddress;
    private String officeAddress;
    private Long personalIdentificationNumber;

}
