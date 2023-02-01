package com.ftr.UserService.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "STATUS")
    private String status;

    @PrePersist
    public void prePersist() {
        this.status = "Active";
    }
}
