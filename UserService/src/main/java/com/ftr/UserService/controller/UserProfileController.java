package com.ftr.UserService.controller;
import com.ftr.UserService.exception.UserProfileException;
import com.ftr.UserService.model.LoginRequest;
import com.ftr.UserService.model.UserProfileRequest;
import com.ftr.UserService.model.UserProfileResponse;
import com.ftr.UserService.model.UserProfileUpdateRequest;
import com.ftr.UserService.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ftr/userProfile")
@Validated
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private Environment environment;

    @PostMapping
    public ResponseEntity<UserProfileResponse> createUser(@Valid @RequestBody UserProfileRequest userProfileRequest) throws UserProfileException {
        UserProfileResponse userProfileResponse = userProfileService.createUser(userProfileRequest);
        String successMessage = environment.getProperty("user.create.success") + userProfileResponse.getUserId();
        return new ResponseEntity<>(userProfileResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_Customer')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable("userId") int userId) throws UserProfileException {
        UserProfileResponse userProfileResponse = userProfileService.getUser(userId);
        String successMessage = environment.getProperty("user.found") + userProfileResponse.getUserId();
        return new ResponseEntity<>(userProfileResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Customer')")
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable("userId") int userId, @Valid @RequestBody UserProfileUpdateRequest userProfileUpdateRequest) throws UserProfileException {
        String updatedMessage = userProfileService.updateUser(userId, userProfileUpdateRequest);
        String successMessage = environment.getProperty("user.update.success") + userId;
        return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId) throws UserProfileException {
        Integer deletedUserId = userProfileService.deleteUser(userId);
        String successMessage = environment.getProperty("user.delete.success") + deletedUserId;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws UserProfileException {
        String successUserId = userProfileService.login(loginRequest);
        String successMessage = environment.getProperty("user.login.success") + successUserId;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

}
