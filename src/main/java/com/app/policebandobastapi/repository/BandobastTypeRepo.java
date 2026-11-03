package com.app.policebandobastapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.policebandobastapi.model.BandobastType;

@Repository 
public interface BandobastTypeRepo  extends JpaRepository<BandobastType, Long>{

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
