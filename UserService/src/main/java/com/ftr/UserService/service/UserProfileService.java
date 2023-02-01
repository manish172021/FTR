package com.ftr.UserService.service;

import com.ftr.UserService.exception.UserProfileException;
import com.ftr.UserService.model.UserProfileRequest;
import com.ftr.UserService.model.UserProfileResponse;
import com.ftr.UserService.model.UserProfileUpdateRequest;

public interface UserProfileService {
    UserProfileResponse createUser(UserProfileRequest userProfileRequest) throws UserProfileException;

    UserProfileResponse getUser(int userId) throws UserProfileException;

    String updateUser(int userId, UserProfileUpdateRequest userProfileUpdateRequest) throws UserProfileException;

    Integer deleteUser(int userId) throws UserProfileException;
}
