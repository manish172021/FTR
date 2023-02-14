package com.ftr.UserService.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "{input.parameter.missing}")
    private int userId;

    @NotNull(message = "{input.parameter.missing}")
    private String password;
}
