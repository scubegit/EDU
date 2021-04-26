package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.VerificationRequest;

@Repository
public interface UniversityVerifierRepository extends JpaRepository<VerificationRequest, Long>{
	@Query(value = "SELECT * FROM verification_request where doc_status in ('Rejected', 'Approved','SV_Approved','SV_Rejected')" , nativeQuery = true)
	List<VerificationRequest> findByStatus();

}
