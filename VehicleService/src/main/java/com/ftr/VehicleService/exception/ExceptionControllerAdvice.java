package com.ftr.VehicleService.exception;

import com.ftr.VehicleService.model.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    @Autowired
    Environment environment;

    // Global Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .errorMessage(environment.getProperty("general.exception"))
                .timeStamp(LocalDateTime.now())
                .errorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // For handling FeignException
    @ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(CustomFeignException exception) {
        return new ResponseEntity<>(new ErrorResponse().builder()
                .errorMessage(exception.getErrorMessage())
                .timeStamp(exception.getTimeStamp())
                .errorCode(exception.getErrorCode())
                .build(), HttpStatusCode.valueOf(exception.getStatus()));
    }

    // handleTerminalException
    @ExceptionHandler(VehicleException.class)
    public ResponseEntity<ErrorResponse> handleTerminalException(VehicleException exception) {
        HttpStatus errorCode = HttpStatus.NOT_FOUND;
        if (exception.getMessage().equals("vehicles.empty")) {
            errorCode = HttpStatus.NOT_FOUND;
        } else if (exception.getMessage().equals("vehicle.notFound")) {
            errorCode = HttpStatus.NOT_FOUND;
        } else if (exception.getMessage().equals("vehicle.nameType.notFound")) {
            errorCode = HttpStatus.NOT_FOUND;
        } else if (exception.getMessage().equals("vehicle.update.alreadyExists")) {
            errorCode = HttpStatus.CONFLICT;
        }else if (exception.getMessage().equals("vehicle.alreadyExists")) {
            errorCode = HttpStatus.CONFLICT;
        } else if (exception.getMessage().equals("vehicle.already.deleted")) {
        errorCode = HttpStatus.CONFLICT;
        } else if (exception.getMessage().equals("vehicle.associated.withWorkItem")) {
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

    // handleMethodArgumentNotValid : triggers when @Valid fails
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorMsg = new ArrayList<String>();
        errorMsg = exception.getBindingResult().getFieldErrors().stream().map(error -> error.getObjectName()+ ": " +error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMessage("Validation Errors:: " + String.join(" || ", errorMsg))
                .build(), HttpStatus.BAD_REQUEST);
    }

    // handleHttpMediaTypeNotSupported : triggers when the JSON is invalid
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder errorMsg = new StringBuilder();
        errorMsg.append("Unsupported Media Type :: ");
        errorMsg.append(exception.getContentType());
        errorMsg.append(" media type is not supported. Supported media types are ");
        exception.getSupportedMediaTypes().forEach(x -> errorMsg.append(x).append(", "));

        return new ResponseEntity<>(ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMessage(String.valueOf(errorMsg))
                .build(), HttpStatus.BAD_REQUEST);
    }

    // handleHttpMessageNotReadable : triggers when the JSON is malformed
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMessage("Malformed JSON request:: " + exception.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    // handleMissingServletRequestParameter : triggers when there are missing parameters
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMsg = exception.getParameterName() + " parameter is missing";

        return new ResponseEntity<>(ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMessage("Missing Parameters:: " + errorMsg)
                .build(), HttpStatus.BAD_REQUEST);
    }


    //  handleNoHandlerFoundException : triggers when the handler method is invalid
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String errorMsg = String.format("Could not find the %s method for URL %s", exception.getHttpMethod(), exception.getRequestURL());
        return new ResponseEntity<>(ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST))
                .errorMessage("Method Not Found:: " + errorMsg)
                .build(), HttpStatus.BAD_REQUEST);
    }

}
