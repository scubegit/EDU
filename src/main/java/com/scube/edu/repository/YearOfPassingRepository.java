
package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.PassingYearMaster;

@Repository
public interface YearOfPassingRepository extends JpaRepository<PassingYearMaster, Long> {
	
	PassingYearMaster  findByYearOfPassing(String yrofpass);
	PassingYearMaster findById(long pid);



}