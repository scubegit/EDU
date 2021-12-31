package com.scube.edu.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.MigrationRequestEntity;

@Repository
public interface MigrationRequestRepository extends JpaRepository<MigrationRequestEntity, Long> {

	MigrationRequestEntity findByCreateby(String userid);

	@Query(value = "select * from list_view", nativeQuery = true)
	List<Map<String, String>> getMigrationRequests();

	@Query(value = "select * from list_view where verAppId = (?1)", nativeQuery = true)
	Map<String, String> findByApplicationId(String applicationId);

}
