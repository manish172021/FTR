package com.ftr.WorkitemService.exception;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CustomFeignException extends RuntimeException {
    private LocalDateTime timeStamp;
    private String errorCode;
    private String errorMessage;
    private Integer status;

    public CustomFeignException(LocalDateTime timeStamp, String errorCode, String errorMessage, int status) {
        super(errorMessage);
        this.timeStamp = timeStamp;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }
}
