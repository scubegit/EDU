package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.RaiseDespute;

@Repository
public interface RaiseDisputeRepository extends JpaRepository<RaiseDespute, Long>{

	RaiseDespute findByApplicationId(Long application_id);

	RaiseDespute findByVerificationId(Long id);

	List<RaiseDespute> findByStatus(String string);
	
	

}
