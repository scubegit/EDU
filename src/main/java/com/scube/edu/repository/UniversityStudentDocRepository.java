package com.scube.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.model.UserMasterEntity;

public interface UniversityStudentDocRepository extends JpaRepository<UniversityStudentDocument, Long> {

	UniversityStudentDocument findByEnrollmentNo(String enrollmentNo);

	

}
