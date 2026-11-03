
package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.ForceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForceRequestRepository extends JpaRepository<ForceRequest, Long> {

    // Get all force requests for a particular Bandobast
    List<ForceRequest> findByBandobastId(Long bandobastId);

    // Get force request of a particular police station for a Bandobast
    Optional<ForceRequest> findByBandobastIdAndPoliceStationId(
            Long bandobastId,
            int policeStationId
    );

    // Check whether a police station has already submitted a request
    boolean existsByBandobastIdAndPoliceStationId(
            Long bandobastId,
            int policeStationId
    );
}
