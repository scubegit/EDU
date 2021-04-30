package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.CollegeMaster;

@Repository
public interface CollegeRepository extends JpaRepository<CollegeMaster, Long> {

	
	CollegeMaster findById(long id);
		CollegeMaster findByCollegeName(String collegeName);	
	CollegeMaster deleteById(long id);
	
}
