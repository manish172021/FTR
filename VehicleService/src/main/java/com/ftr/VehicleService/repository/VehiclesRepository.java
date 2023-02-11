package com.ftr.VehicleService.repository;

import com.ftr.VehicleService.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehiclesRepository extends JpaRepository<Vehicle, String> {
    public List<Vehicle> findByVehicleName(String vehicleName);
    public List<Vehicle> findByHarborLocation(String harborLocation);
}
