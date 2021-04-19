package com.scube.edu.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.Convert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.model.UserMasterEntity;

@Repository
public interface UniversityStudentDocRepository extends JpaRepository<UniversityStudentDocument, Long> {

	UniversityStudentDocument findByEnrollmentNo(String enrollmentNo);

	/*
	 * @Query(nativeQuery = true, value= ":query") List<UniversityStudentDocument>
	 * getStudData(@Param("query") String query) ;
	 */
	
}
