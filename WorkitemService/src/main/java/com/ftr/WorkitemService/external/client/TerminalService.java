package com.ftr.WorkitemService.external.client;

import com.ftr.WorkitemService.exception.CustomFeignException;
import com.ftr.WorkitemService.external.client.response.TerminalResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.List;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "TERMINAL-SERVICE/ftr/terminals")
public interface TerminalService {

    @PutMapping("/{terminalId}/{newCapacity}")
    public ResponseEntity<String> updateTerminal(@PathVariable("terminalId") String terminalId, @PathVariable("newCapacity") int newCapacity);

    @GetMapping("/fetchTerminalByItemType/{itemType}")
    public ResponseEntity<List<TerminalResponse>> fetchTerminalByItemType(@PathVariable("itemType") String itemType);

    default ResponseEntity<Long> fallback(Exception e) {
        LocalDateTime timeStamp = LocalDateTime.now();
        String errorCode = String.valueOf(HttpStatus.SERVICE_UNAVAILABLE);
        String errorMessage = "Terminal Service is not available!";
        int status = HttpStatus.SERVICE_UNAVAILABLE.value();
        throw new CustomFeignException(timeStamp, errorCode, errorMessage, status);
    }
}
