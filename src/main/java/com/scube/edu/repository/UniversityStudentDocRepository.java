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

	@Query(value = "SELECT * FROM university_studentdocument where enrollment_no = ?1 and first_name = ?2 "
			+ "and last_name = ?3 and stream = ?4 and passing_year = ?5 and college_name = ?6" , nativeQuery = true)
//	@Query(value = "SELECT * FROM university_studentdocument where enrollment_no = ?1 ", nativeQuery = true)
	UniversityStudentDocument getDocDataBySixFields(String enrollmentNo , String firstName, String lastName, String streamName, String yearOfPassing, String collegeName);

	UniversityStudentDocument findByEnrollmentNoAndFirstNameAndLastNameAndStreamIdAndCollegeIdAndPassingYearId(String enrollmentNo,String fnm,String lastnm,Long stream,Long clgnm, Long passyr);

	
	/*
	 * @Query(nativeQuery = true, value= ":query") List<UniversityStudentDocument>
	 * getStudData(@Param("query") String query) ;
	 */

}
