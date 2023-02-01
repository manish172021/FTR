package com.ftr.UserService.exception;

import lombok.Data;

@Data
public class UserProfileException extends Exception {
    private static final long serialVersionUID = 1L;

    public UserProfileException(String message) {
        super(message);
    }
}
