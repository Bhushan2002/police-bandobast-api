package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShiftRepo extends JpaRepository<Shift, Integer> {
}
