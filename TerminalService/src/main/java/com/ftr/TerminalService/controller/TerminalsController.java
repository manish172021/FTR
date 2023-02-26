package com.ftr.TerminalService.controller;

import com.ftr.TerminalService.exception.TerminalsException;
import com.ftr.TerminalService.model.TerminalRequest;
import com.ftr.TerminalService.model.TerminalResponse;
import com.ftr.TerminalService.service.TerminalsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/ftr/terminals")
@Validated
public class TerminalsController {
    @Autowired
    private TerminalsService terminalsService;

    @Autowired
    private Environment environment;

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @PostMapping
    public ResponseEntity<TerminalResponse> insertNewTerminal(@RequestBody @Valid TerminalRequest terminalsRequest) {
        TerminalResponse terminalsResponse = terminalsService.insertNewTerminal(terminalsRequest);
        String successMessage = environment.getProperty("terminal.insert.success") + terminalsResponse.getTerminalId();
        return new ResponseEntity<>(terminalsResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @GetMapping
    public ResponseEntity<List<TerminalResponse>> fetchFTRTerminals() throws TerminalsException {
        List<TerminalResponse> terminalsResponses = terminalsService.fetchFTRTerminals();
        String successMessage = environment.getProperty("terminals.fetch.success");
        return new ResponseEntity<>(terminalsResponses, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin') || hasAuthority('internal')")
    @PutMapping("/{terminalId}/{newCapacity}")
    public ResponseEntity<String> updateTerminal(@PathVariable("terminalId") String terminalId, @PathVariable("newCapacity") int newCapacity) throws TerminalsException {
        String updatedMessage = terminalsService.updateTerminal(terminalId, newCapacity);
        String successMessage = updatedMessage + terminalId;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin') || hasAuthority('internal')")
    @GetMapping("/fetchTerminalByItemType/{itemType}")
    public ResponseEntity<List<TerminalResponse>> fetchTerminalByItemType(@PathVariable("itemType") String itemType) throws TerminalsException {
        List<TerminalResponse> terminalsResponses = terminalsService.fetchTerminalsByItemType(itemType);
        String successMessage = environment.getProperty("terminals.fetch.itemType.success");
        return new ResponseEntity<>(terminalsResponses, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @DeleteMapping("/{terminalId}")
    public ResponseEntity<String> removeTerminal(@PathVariable("terminalId") String terminalId) throws TerminalsException {
        String deletedTerminalId = terminalsService.removeTerminal(terminalId);
        String successMessage = environment.getProperty("terminal.delete.success") + deletedTerminalId;
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Admin')")
    @GetMapping("/fetchTerminalByTerminalId/{terminalId}")
    public ResponseEntity<TerminalResponse> fetchTerminalByTerminalId(@PathVariable("terminalId") String terminalId) throws TerminalsException {
        TerminalResponse terminalsResponse = terminalsService.fetchTerminalByTerminalId(terminalId);
        String successMessage = environment.getProperty("terminals.fetch.terminalId.success");
        return new ResponseEntity<>(terminalsResponse, HttpStatus.OK);
    }
}
