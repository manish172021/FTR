package com.ftr.TerminalService.exception;

import lombok.Data;

@Data
public class TerminalsException extends Exception {
    private static final long serialVersionUID = 1L;

    public TerminalsException(String message) {
        super(message);
    }
}
