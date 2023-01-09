package com.ftr.UserService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "MOBILE_NUMBER")
    private Long mobileNumber;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "PASSPORT_NUMBER")
    private String passportNumber;

    @Column(name = "PERMANENT_ADDRESS")
    private String permanentAddress;

    @Column(name = "OFFICE_ADDRESS")
    private String officeAddress;

    @Column(name = "PERSONAL_IDENTIFICATION_NUMBER")
    private Long personalIdentificationNumber;

}
