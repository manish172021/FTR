package com.ftr.UserService.exception;


import jakarta.annotation.Priority;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ftr.UserService.model.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    @Autowired
    Environment environment;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(environment.getProperty(exception.getMessage()))
                .timeStamp(LocalDateTime.now())
                .errorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    // handleHttpMediaTypeNotSupported : triggers when the JSON is invalid
//    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception) {
//        StringBuilder errorMsg = new StringBuilder();
//        errorMsg.append("Unsupported Media Type :: ");
//        errorMsg.append(exception.getContentType());
//        errorMsg.append(" media type is not supported. Supported media types are ");
//        exception.getSupportedMediaTypes().forEach(x -> errorMsg.append(x).append(", "));
//
//        return new ResponseEntity<>(ErrorResponse.builder()
//                .timeStamp(LocalDateTime.now())
//                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
//                .errorMessage(String.valueOf(errorMsg))
//                .build(), HttpStatus.BAD_REQUEST);
//    }

//    // handleHttpMessageNotReadable : triggers when the JSON is malformed
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
//        return new ResponseEntity<>(ErrorResponse.builder()
//                .timeStamp(LocalDateTime.now())
//                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
//                .errorMessage("Malformed JSON request:: " + exception.getMessage())
//                .build(), HttpStatus.BAD_REQUEST);
//    }

//    // handleMethodArgumentNotValid : triggers when @Valid fails
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
//        String errorMsg = exception.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
//                .collect(Collectors.joining(", "));
//        return new ResponseEntity<>(ErrorResponse.builder()
//                .timeStamp(LocalDateTime.now())
//                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
//                .errorMessage("Validation Errors:: " + errorMsg)
//                .build(), HttpStatus.BAD_REQUEST);
//    }

//    // handleMissingServletRequestParameter : triggers when there are missing parameters
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception) {
//        String errorMsg = exception.getParameterName() + " parameter is missing";
//        return new ResponseEntity<>(ErrorResponse.builder()
//                .timeStamp(LocalDateTime.now())
//                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
//                .errorMessage("Missing Parameters:: " + errorMsg)
//                .build(), HttpStatus.BAD_REQUEST);
//    }

    // handleMethodArgumentTypeMismatch : triggers when a parameter's type does not match
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMessage("Mismatch Type:: " + exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    // handleConstraintViolationException : triggers when @Validated fails
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
//        String errorMsg = exception.getConstraintName() .stream().map(x -> x.getMessage())
//                .collect(Collectors.joining(", "));
        String errorMsg = exception.getMessage();
        return new ResponseEntity<>(ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMessage("Constraint Violation:: " + errorMsg)
                .build(), HttpStatus.BAD_REQUEST);
    }

    // handleUserProfileException : triggers when there is not user with the specified userId
    @ExceptionHandler(UserProfileException.class)
    public ResponseEntity<ErrorResponse> handleUserProfileException(UserProfileException exception) {
        HttpStatus errorCode = HttpStatus.NOT_FOUND;
        if (exception.getMessage().equals("user.alreadyExists")) {
            errorCode = HttpStatus.CONFLICT;
        } else if (exception.getMessage().equals("user.notFound")) {
            errorCode = HttpStatus.NOT_FOUND;
        } else if (exception.getMessage().equals("user.update.fail")) {
            errorCode = HttpStatus.BAD_REQUEST;
        }
        if (exception.getMessage().equals("user.alreadyDeleted")) {
            errorCode = HttpStatus.CONFLICT;
        } else {
            errorCode = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(String.valueOf(errorCode))
                .errorMessage(environment.getProperty(exception.getMessage()))
                .build(), errorCode);
    }

//    // handleNoHandlerFoundException : triggers when the handler method is invalid
////    @ExceptionHandler(NoHandlerFoundException.class)
//    @Override
//    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException exception) {
//        String errorMsg = String.format("Could not find the %s method for URL %s", exception.getHttpMethod(), exception.getRequestURL());
//        return new ResponseEntity<>(ErrorResponse.builder()
//                .timeStamp(LocalDateTime.now())
//                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
//                .errorMessage("Method Not Found:: " + errorMsg)
//                .build(), HttpStatus.BAD_REQUEST);
//    }

}
