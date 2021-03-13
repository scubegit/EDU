package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;

import com.scube.edu.response.StudentVerificationDocsResponse;

@Repository
public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long>{

	List<VerificationRequest> findByUserId(long userId);

	@Query(value = "SELECT * FROM verification_request where doc_status = 'Requested' and assigned_to = 0 order by created_date desc limit 5", nativeQuery = true)
	List<VerificationRequest> getVerifierRecords();

	@Query(value = "SELECT * FROM verification_request where doc_status = 'Verified' and user_id = ?1" , nativeQuery = true)
	List<VerificationRequest> findByStatusAndUserId(long userId);
	

}
