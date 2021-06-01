package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.FinancialYearEntity;

@Repository
public interface FinancialYearRepository extends JpaRepository<FinancialYearEntity, Long> {

	@Query(value="SELECT MAX(fin_year) FROM financial_year",nativeQuery = true)
	String findMaxYear();
	
}
