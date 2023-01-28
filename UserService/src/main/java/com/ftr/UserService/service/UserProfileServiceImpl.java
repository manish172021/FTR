package com.ftr.UserService.service;

import com.ftr.UserService.entity.UserProfile;
import com.ftr.UserService.exception.UserProfileException;
import com.ftr.UserService.model.UserProfileRequest;
import com.ftr.UserService.model.UserProfileResponse;
import com.ftr.UserService.repository.UserProfileRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfileResponse createUser(UserProfileRequest userProfileRequest) throws UserProfileException {
        // log.info("Creating User...");

        Optional<UserProfile> optionalUserProfile = Optional.ofNullable(userProfileRepository.
                findByPersonalIdentificationNumber(
                        userProfileRequest
                                .getPersonalIdentificationNumber()));

        if(optionalUserProfile.isPresent() && optionalUserProfile.get().getStatus().equals("DELETED")) {
            throw new UserProfileException("user.alreadyDeleted");
        }

        if(optionalUserProfile.isPresent())
            throw new UserProfileException("user.alreadyExists");

        UserProfile userProfile
                = UserProfile.builder()
                .firstName(userProfileRequest.getFirstName())
                .lastName(userProfileRequest.getLastName())
                .emailId(userProfileRequest.getEmailId())
                .mobileNumber(userProfileRequest.getMobileNumber())
                .password(userProfileRequest.getPassword())
                .nationality(userProfileRequest.getNationality())
                .passportNumber(userProfileRequest.getPassportNumber())
                .permanentAddress(userProfileRequest.getPermanentAddress())
                .officeAddress(userProfileRequest.getOfficeAddress())
                .personalIdentificationNumber(userProfileRequest.getPersonalIdentificationNumber())
                .build();

        userProfileRepository.save(userProfile);
        // log.info("User Created...");

        UserProfileResponse userProfileResponse = new UserProfileResponse();
        BeanUtils.copyProperties(userProfile, userProfileResponse);
        return userProfileResponse;
    }

    @Override
    public UserProfileResponse getUser(int userId) throws UserProfileException {
        // log.info("Getting the userProfile for userId: {}", userId);
        UserProfile userProfile = userProfileRepository.findById(userId)
                .filter(deletedUserProfile -> !deletedUserProfile.getStatus().equals("DELETED"))
                .orElseThrow(() -> new UserProfileException("user.notFound"));
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        BeanUtils.copyProperties(userProfile, userProfileResponse);
        // log.info("Got the userProfile for userId: {}", userId);
        return userProfileResponse;
    }

    @Override
    public String updateUser(int userId, UserProfileRequest userProfileRequest) throws UserProfileException {
        // log.info("Updating the userProfile for userId: {} ...", userId);

        UserProfile userProfile = userProfileRepository.findById(userId)
                .filter(deletedUserProfile -> !deletedUserProfile.getStatus().equals("DELETED"))
                .orElseThrow(() -> new UserProfileException("user.notFound"));

        String response = "";

        try {
            if (userProfileRequest.getFirstName() != null) {
                userProfile.setFirstName(userProfileRequest.getFirstName());
                response += "FirstName, ";
            }
            if (userProfileRequest.getLastName() != null) {
                userProfile.setLastName(userProfileRequest.getLastName());
                response += "LastName, ";
            }
            if (userProfileRequest.getEmailId() != null) {
                userProfile.setEmailId(userProfileRequest.getEmailId());
                response += "EmailId, ";
            }
            if (userProfileRequest.getMobileNumber() != null) {
                userProfile.setMobileNumber(userProfileRequest.getMobileNumber());
                response += "MobileNumber, ";
            }
            if (userProfileRequest.getPassword() != null && userProfileRequest.getPassword() == userProfile.getPassword()) {
                userProfile.setPassword(userProfileRequest.getPassword());
                response += "Password, ";
            }
            if (userProfileRequest.getNationality() != null) {
                userProfile.setNationality(userProfileRequest.getNationality());
                response += "Nationality, ";
            }
            if (userProfileRequest.getPassportNumber() != null) {
                userProfile.setPassword(userProfileRequest.getPassportNumber());
                response += "PassportNumber, ";
            }
            if (userProfileRequest.getPermanentAddress() != null) {
                userProfile.setPermanentAddress(userProfileRequest.getPermanentAddress());
                response += "PermanentAddress, ";
            }
            if (userProfileRequest.getOfficeAddress() != null) {
                userProfile.setOfficeAddress(userProfileRequest.getOfficeAddress());
                response += "OfficeAddress, ";
            }
            if (userProfileRequest.getPersonalIdentificationNumber() != null) {
                userProfile.setPersonalIdentificationNumber(userProfileRequest.getPersonalIdentificationNumber());
                response += "PersonalIdentificationNumber, ";
            }
        }
        catch (Exception userProfileException) {
            throw new UserProfileException("user.update.fail");
        }

        if(!response.isBlank()) {
            response = response.substring(0, response.length()-2) + " details are updated successfully for UserId: " + userId;
            int lastIndex = response.lastIndexOf(", ");
            if(lastIndex != -1) {
                response = response.substring(0, lastIndex).concat(" and").concat(response.substring(lastIndex + 1));
            }
        }

        userProfileRepository.save(userProfile);
        // log.info("Updated the userProfile for userId: {} ...", userId);
        return response;
    }

    @Override
    public Integer deleteUser(int userId) throws UserProfileException {
        // log.info("Deleting userId: {} ...", userId);

        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new UserProfileException("user.notFound"));

        if(userProfile.getStatus().equals("DELETED")) {
            throw new UserProfileException("user.alreadyDeleted");
        }

        userProfile.setStatus("DELETED");
        userProfileRepository.saveAndFlush(userProfile);
        // userProfileRepository.delete(userProfile);
        // log.info("Deleted userId: {}...", userId);
        return userId;
    }
}
