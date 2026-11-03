
package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.DutyPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DutyPointRepository extends JpaRepository<DutyPoint, Long> {

    List<DutyPoint> findByBandobastId(Long bandobastId);

}
