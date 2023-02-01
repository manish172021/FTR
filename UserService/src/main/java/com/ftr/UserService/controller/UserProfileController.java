package com.ftr.UserService.controller;
import com.ftr.UserService.exception.UserProfileException;
import com.ftr.UserService.model.UserProfileRequest;
import com.ftr.UserService.model.UserProfileResponse;
import com.ftr.UserService.model.UserProfileUpdateRequest;
import com.ftr.UserService.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ftr/userProfile")
@Validated
public class UserProfileController {

    /* TODO
        login( LoginDTO logindto) POST	ResponseEntity<String>
        This method is used for logging in using userId and password
    */

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

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> getUser(@PathVariable("userId") int userId) throws UserProfileException {
        UserProfileResponse userProfileResponse = userProfileService.getUser(userId);
        String successMessage = environment.getProperty("user.found") + userProfileResponse.getUserId();
        return new ResponseEntity<>(userProfileResponse, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable("userId") int userId, @Valid @RequestBody UserProfileUpdateRequest userProfileUpdateRequest) throws UserProfileException {
        String updatedMessage = userProfileService.updateUser(userId, userProfileUpdateRequest);
        String successMessage = environment.getProperty("user.update.success") + userId;
        return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId) throws UserProfileException {
        Integer deletedUserId = userProfileService.deleteUser(userId);
        String successMessage = environment.getProperty("user.delete.success") + deletedUserId;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
/* TODO
    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO logindto) {
        String updateMessage = userProfileService.login(logindto);
        return new ResponseEntity<>(updateMessage, HttpStatus.OK);
    }
 */

}
