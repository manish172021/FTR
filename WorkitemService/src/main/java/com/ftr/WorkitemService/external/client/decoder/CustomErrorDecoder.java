package com.ftr.WorkitemService.external.client.decoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ftr.WorkitemService.exception.CustomFeignException;
import com.ftr.WorkitemService.model.ErrorResponse;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;


@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    @Autowired
    Environment environment;

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = FeignException.errorStatus(methodKey, response);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register the JSR310 module

        String responseString = exception.getMessage();
        String jsonStart = responseString.substring(responseString.indexOf("[{"));
        String jsonEnd = jsonStart.substring(1, jsonStart.lastIndexOf("]"));

        try {
            ErrorResponse errorResponse = objectMapper.readValue(jsonEnd, ErrorResponse.class);
            return new CustomFeignException(
                    errorResponse.getTimeStamp(), errorResponse.getErrorCode(), errorResponse.getErrorMessage(), response.status());
        }
        catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
