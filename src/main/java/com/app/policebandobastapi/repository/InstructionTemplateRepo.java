package com.app.policebandobastapi.repository;


import com.app.policebandobastapi.model.InstructionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionTemplateRepo extends JpaRepository<InstructionTemplate, Integer> {
}
