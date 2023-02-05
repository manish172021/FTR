package com.ftr.VehicleService.exception;

import lombok.Data;

@Data
public class VehicleException extends Exception {
    private static final long serialVersionUID = 1L;

    public VehicleException(String message) {
        super(message);
    }
}
