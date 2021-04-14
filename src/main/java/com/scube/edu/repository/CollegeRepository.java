package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.UserMasterEntity;

@Repository
public interface CollegeRepository extends JpaRepository<CollegeMaster, Long> {

	
	CollegeMaster findById(long id);
	
}
