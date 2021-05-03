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
import com.scube.edu.response.UniversityStudentDocumentResponse;

@Repository
public interface UniversityStudentDocRepository extends JpaRepository<UniversityStudentDocument, Long> {

	@Query(value = "SELECT * FROM university_studentdocument where enrollment_no = ?1 and first_name = ?2 "
			+ "and last_name = ?3 and stream_id = ?4 and passing_year_id = ?5 and college_id = ?6" , nativeQuery = true)
//	@Query(value = "SELECT * FROM university_studentdocument where enrollment_no = ?1 ", nativeQuery = true)
	UniversityStudentDocument getDocDataBySixFields(String enrollmentNo , String firstName, String lastName, Long streamId, String yearOfPassing, Long collegeId);

	UniversityStudentDocument findByEnrollmentNoAndFirstNameAndLastNameAndStreamIdAndCollegeIdAndPassingYearId(String enrollmentNo,String fnm,String lastnm,Long stream,Long clgnm, Long passyr);

	
	@Query(value = "SELECT * FROM university_studentdocument where first_name LIKE %?1% and last_name LIKE %?2% and enrollment_no LIKE %?3% and college_id LIKE %?4% and passing_year_id LIKE %?5% and stream_id LIKE %?6%" , nativeQuery = true)
	List<UniversityStudentDocument> searchByFirstNameLikeAndLastNameLikeAndEnrollmentNoLikeAndCollegeIdLikeAndPassingYearIdLikeAndStreamIdLike( String firstname, 
			String lastname, String enrollmentno, String collegeid, String yearofpassid, String streamid);



}
