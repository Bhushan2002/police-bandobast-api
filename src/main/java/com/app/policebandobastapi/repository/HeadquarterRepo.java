package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.Headquarter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HeadquarterRepo extends JpaRepository<Headquarter, Integer> {
}
