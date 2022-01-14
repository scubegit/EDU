package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scube.edu.model.VerificationRequest;

public interface RemainderMailtoEditRequestRepository extends JpaRepository<VerificationRequest, Long>{
	
	@Query(value = "SELECT * FROM verification_request where doc_status='Ver_Request_Edit'", nativeQuery = true)
	List<VerificationRequest> getListOfEditRequest();

}
