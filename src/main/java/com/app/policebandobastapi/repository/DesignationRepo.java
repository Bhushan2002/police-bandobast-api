package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DesignationRepo extends JpaRepository<Designation, Integer> {
}
