package com.ftr.TerminalService.service;

import com.ftr.TerminalService.entity.Terminal;
import com.ftr.TerminalService.exception.TerminalsException;
import com.ftr.TerminalService.model.TerminalRequest;
import com.ftr.TerminalService.model.TerminalResponse;
import com.ftr.TerminalService.repository.TerminalsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class TerminalsServiceImpl implements TerminalsService {

    @Autowired
    private TerminalsRepository terminalsRepository;

    @Autowired
    private Environment environment;

    @Override
    public TerminalResponse insertNewTerminal(TerminalRequest terminalsRequest) {
        log.info("Inserting terminal...");

        Terminal terminal
                = Terminal.builder()
                .terminalName(terminalsRequest.getTerminalName())
                .country(terminalsRequest.getCountry())
                .itemType(terminalsRequest.getItemType())
                .terminalDescription(terminalsRequest.getTerminalDescription())
                .capacity(terminalsRequest.getCapacity())
                .status(terminalsRequest.getStatus())
                .harborLocation(terminalsRequest.getHarborLocation())
                .availableCapacity(terminalsRequest.getAvailableCapacity())
                .build();
        terminalsRepository.save(terminal);

        TerminalResponse terminalResponse = new TerminalResponse();
        BeanUtils.copyProperties(terminal, terminalResponse);
        return terminalResponse;
    }

    @Override
    public List<TerminalResponse> fetchFTRTerminals() throws TerminalsException {
        log.info("fetching terminals...");
        List<Terminal> terminals = terminalsRepository.findAll()
                .stream()
                .filter(terminal -> !terminal.getDeleted_status().equals("DELETED"))
                        .collect(Collectors.toList());

        if (terminals == null) {
            throw new TerminalsException("terminal.empty");
        }

        List<TerminalResponse> terminalsResponse = terminals
                .stream()
                .map(terminal -> {
                    TerminalResponse terminalResponse = new TerminalResponse();
                    BeanUtils.copyProperties(terminal, terminalResponse);
                    return terminalResponse;
                })
                .collect(Collectors.toList());

        log.info("terminal fetched...");
        return terminalsResponse;
    }

    @Override
    public String updateTerminal(String terminalId, int newCapacity) throws TerminalsException {
        Terminal terminal = terminalsRepository.findById(terminalId)
                .filter(deleteTerminal -> !deleteTerminal.getDeleted_status().equals("DELETED"))
                .orElseThrow(() -> new TerminalsException("terminal.notFound"));

        Integer availableCapacity =  terminal.getCapacity();
        if(newCapacity > availableCapacity) {
            throw new TerminalsException("terminal.capacity.failed");
        }
        if(newCapacity == availableCapacity) {
            terminal.setStatus("Not Available");
        }
        terminal.setCapacity(availableCapacity - newCapacity);
        terminalsRepository.saveAndFlush(terminal);
        log.info("terminal capacity updated...");
        String response = environment.getProperty("terminal.update.success");
        return response;
    }

    @Override
    public List<TerminalResponse> fetchTerminalsByItemType(String itemType) throws TerminalsException {
        log.info("fetching terminals by Item Type...");
        List<Terminal> terminals = terminalsRepository.findByItemType(itemType)
                .stream()
                .filter(terminal -> !terminal.getDeleted_status().equals("DELETED"))
                .collect(Collectors.toList());
        if (terminals == null) {
            throw new TerminalsException("terminal.itemtype.notFound");
        }
        List<TerminalResponse> terminalsResponse = terminals
                .stream()
                .map(terminal -> {
                    TerminalResponse terminalResponse = new TerminalResponse();
                    BeanUtils.copyProperties(terminal, terminalResponse);
                    return terminalResponse;
                })
                .collect(Collectors.toList());

        log.info("fetched terminals by Item Type...");
        return terminalsResponse;
    }

    @Override
    public String removeTerminal(String terminalId) throws TerminalsException {
        log.info("removing terminal...");
        Terminal terminal = terminalsRepository.findById(terminalId)
                .orElseThrow(() -> new TerminalsException("terminal.notFound"));
        if(terminal.getDeleted_status().equals("DELETED")) {
            throw new RuntimeException("terminal.already.deleted");
        }
        terminal.setDeleted_status("DELETED");
        terminalsRepository.saveAndFlush(terminal);
        log.info("removed terminal...");
        return terminalId;
    }

    @Override
    public TerminalResponse fetchTerminalByTerminalId(String terminalId) throws TerminalsException {
        log.info("fetching terminal by id...");
        Terminal terminal = terminalsRepository.findById(terminalId)
                .orElseThrow(() -> new TerminalsException("terminal.notFound"));

        TerminalResponse terminalResponse = new TerminalResponse();
        BeanUtils.copyProperties(terminal, terminalResponse);
        log.info("fetched terminal by id...");
        return terminalResponse;
    }
}
