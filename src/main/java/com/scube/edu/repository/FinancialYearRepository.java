package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.FinancialYearEntity;

@Repository
public interface FinancialYearRepository extends JpaRepository<FinancialYearEntity, Long> {
	
}
