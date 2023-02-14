package com.ftr.WorkitemService.repository;

import com.ftr.WorkitemService.entity.Workitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkitemRepository extends JpaRepository<Workitem, String> {
     Workitem findByUserId(Long userId);
}
