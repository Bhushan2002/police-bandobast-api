package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.RejectionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RejectionTemplateRepo extends JpaRepository<RejectionTemplate, Integer> {
}
