package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.CutomizationEntity;

@Repository
public interface CustomizationRepository extends JpaRepository<CutomizationEntity, Long> {
		
	CutomizationEntity findByRoleId(Long roleId);

	@Query(value = "select * from cutomization limit 1", nativeQuery = true)
	CutomizationEntity findServiceFlag();
}
