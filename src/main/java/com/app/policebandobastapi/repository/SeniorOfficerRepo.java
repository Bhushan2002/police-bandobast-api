package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.SeniorOfficer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SeniorOfficerRepo extends JpaRepository<SeniorOfficer, Integer> {
}
