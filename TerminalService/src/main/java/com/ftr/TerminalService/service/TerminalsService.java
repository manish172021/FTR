package com.ftr.TerminalService.service;

import com.ftr.TerminalService.exception.TerminalsException;
import com.ftr.TerminalService.model.TerminalRequest;
import com.ftr.TerminalService.model.TerminalResponse;

import java.util.List;

public interface TerminalsService {

    TerminalResponse insertNewTerminal(TerminalRequest terminalsRequest);

    List<TerminalResponse> fetchFTRTerminals() throws TerminalsException;

    String updateTerminal(String terminalId, int newCapacity) throws TerminalsException;

    List<TerminalResponse> fetchTerminalsByItemType(String itemType) throws TerminalsException;

    String removeTerminal(String terminalId) throws TerminalsException;

    TerminalResponse fetchTerminalByTerminalId(String terminalId) throws TerminalsException;

}
