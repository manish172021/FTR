package com.ftr.TerminalService.repository;

import com.ftr.TerminalService.entity.Terminal;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TerminalsRepository extends JpaRepository<Terminal, String> {
    public List<Terminal> findByItemType(String itemType);
}
