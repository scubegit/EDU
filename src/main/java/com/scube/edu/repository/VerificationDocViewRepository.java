package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.VerificationDocView;
import com.scube.edu.model.VerificationRequest;

@Repository
public interface VerificationDocViewRepository extends JpaRepository<VerificationDocView, Long>{

	List<VerificationDocView> findByUserId(long userId);
	
	List<VerificationDocView> findAll();

}
