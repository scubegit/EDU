package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.MigrationRequestEntity;

@Repository
public interface MigrationRequestRepository extends JpaRepository<MigrationRequestEntity, Long> {

}
