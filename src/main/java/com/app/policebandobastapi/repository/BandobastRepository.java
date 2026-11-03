
package com.app.policebandobastapi.repository;

import com.app.policebandobastapi.model.Bandobast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandobastRepository extends JpaRepository<Bandobast, Long> {

}
