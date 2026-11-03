package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.DigitalSignature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalSignatureRepo extends JpaRepository<DigitalSignature, Long> {
}
