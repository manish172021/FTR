package com.ftr.WorkitemService.repository;

import com.ftr.WorkitemService.entity.WorkitemTerminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkitemTerminalRepository extends JpaRepository<WorkitemTerminal, String> {
}
