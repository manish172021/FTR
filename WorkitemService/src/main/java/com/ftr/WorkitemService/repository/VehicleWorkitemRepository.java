package com.ftr.WorkitemService.repository;

import com.ftr.WorkitemService.entity.VehicleWorkItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleWorkitemRepository extends JpaRepository<VehicleWorkItem, String> {
//    VehicleWorkItem findByWorkitemId(String workitemId);
}
