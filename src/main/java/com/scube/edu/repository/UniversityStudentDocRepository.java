package com.scube.edu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.model.UserMasterEntity;

@Repository
public interface UniversityStudentDocRepository extends JpaRepository<UniversityStudentDocument, Long> {

	UniversityStudentDocument findByEnrollmentNo(String enrollmentNo);

	//@Query("SELECT * FROM UniversityStudentDocument ms "
			//+ "where ms.passingYear=?1 or ms.stream=?2 or ms.collegeName=?3")
	List<UniversityStudentDocument> findAllByCollegeNameOrStreamOrPassingYear(String clgNm ,String  stream,Integer yearofPassing);
	
	
}
