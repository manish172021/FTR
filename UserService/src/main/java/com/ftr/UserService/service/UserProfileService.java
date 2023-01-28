package com.ftr.UserService.service;

import com.ftr.UserService.exception.UserProfileException;
import com.ftr.UserService.model.UserProfileRequest;
import com.ftr.UserService.model.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse createUser(UserProfileRequest userProfileRequest) throws UserProfileException;

    UserProfileResponse getUser(int userId) throws UserProfileException;

    String updateUser(int userId, UserProfileRequest userProfileRequest) throws UserProfileException;

    Integer deleteUser(int userId) throws UserProfileException;
}
