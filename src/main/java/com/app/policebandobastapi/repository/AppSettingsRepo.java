package com.app.policebandobastapi.repository;

import java.util.Optional;

import com.app.policebandobastapi.model.AppSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppSettingsRepo extends JpaRepository<AppSettings, Long> {

    // Settings are a single row
    Optional<AppSettings> findFirstByOrderByIdAsc();
}
