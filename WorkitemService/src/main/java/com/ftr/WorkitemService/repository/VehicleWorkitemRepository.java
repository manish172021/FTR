package com.ftr.WorkitemService.repository;

import com.ftr.WorkitemService.entity.VehicleWorkitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleWorkitemRepository extends JpaRepository<VehicleWorkitem, String> {
    VehicleWorkitem findByWorkitemId(String workitemId);
}
