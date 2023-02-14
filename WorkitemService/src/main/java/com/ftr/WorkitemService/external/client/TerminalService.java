package com.ftr.WorkitemService.external.client;

import com.ftr.WorkitemService.external.client.response.TerminalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;


@FeignClient(name = "TERMINAL-SERVICE/ftr/terminals")
public interface TerminalService {

    @PutMapping("/{terminalId}/{newCapacity}")
    public ResponseEntity<String> updateTerminal(@PathVariable("terminalId") String terminalId, @PathVariable("newCapacity") int newCapacity);

    @GetMapping("/fetchTerminalByItemType/{itemType}")
    public ResponseEntity<List<TerminalResponse>> fetchTerminalByItemType(@PathVariable("itemType") String itemType);
}
