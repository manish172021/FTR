package com.ftr.WorkitemService.repository;

import com.ftr.WorkitemService.entity.Harbor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HarborRepository extends JpaRepository<Harbor, String> {
    List<Harbor> findByCountry(String country);
}
