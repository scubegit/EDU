package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.VerificationRequest;

@Repository
public interface UniversityVerifierRepository extends JpaRepository<VerificationRequest, Long>{
	@Query(value = "SELECT * FROM verification_request where doc_status in ('Approved_Pass', 'Approved_Fail ','SV_Approved_Pass','SV_Approved_Fail','SV_Rejected') order by  created_date desc" , nativeQuery = true)
	List<VerificationRequest> findByStatus();

}
