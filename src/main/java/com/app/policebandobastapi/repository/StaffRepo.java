package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long> {

    boolean existsByBuckleNoIgnoreCase(String buckleNo);

    boolean existsByBuckleNoIgnoreCaseAndIdNot(String buckleNo, Long id);

    boolean existsByMobileNo(String mobileNo);

    boolean existsByMobileNoAndIdNot(String mobileNo, Long id);
}
