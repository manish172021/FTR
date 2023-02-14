package com.ftr.WorkitemService.exception;

import lombok.Data;

@Data
public class WorkitemException extends Exception {
    private static final long serialVersionUID = 1L;

    public WorkitemException(String message) {
        super(message);
    }

}
