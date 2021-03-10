package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.response.StudentDocsResponse;

@Repository
public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long>{

	List<VerificationRequest> findByUserId(long userId);

	
	

}
