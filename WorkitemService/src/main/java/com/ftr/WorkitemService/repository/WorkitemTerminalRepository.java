package com.ftr.WorkitemService.repository;

import com.ftr.WorkitemService.entity.WorkItemTerminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkitemTerminalRepository extends JpaRepository<WorkItemTerminal, String> {
}
