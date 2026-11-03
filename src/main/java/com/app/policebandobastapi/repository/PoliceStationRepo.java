package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.PoliceStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliceStationRepo extends JpaRepository<PoliceStation, Long> {

    boolean existsByMobile(String mobile);

    boolean existsByMobileAndIdNot(String mobile, Long id);
}
