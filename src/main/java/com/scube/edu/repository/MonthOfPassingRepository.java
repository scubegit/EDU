package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.MonthOfPassing;

@Repository
public interface MonthOfPassingRepository extends JpaRepository<MonthOfPassing, Long>{
	
}
