package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scube.edu.model.CutomizationEntity;

public interface CustomizationRepository extends JpaRepository<CutomizationEntity, Long> {
		
	CutomizationEntity findByRoleId(Long roleId);
}
