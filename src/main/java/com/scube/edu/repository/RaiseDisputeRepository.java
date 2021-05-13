package com.scube.edu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.RaiseDespute;
import com.scube.edu.model.VerificationRequest;

@Repository
public interface RaiseDisputeRepository extends JpaRepository<RaiseDespute, Long>{

	RaiseDespute findByApplicationId(Long application_id);

	RaiseDespute findByVerificationId(Long id);

	List<RaiseDespute> findByStatus(String string);

	@Query(value ="SELECT count(*) from raise_despute where year(created_date) = ?1", nativeQuery = true)
	int getstatdisputByYear(int year);
	
	@Query(value ="SELECT count(*) from raise_despute where year(created_date) = ?1 and status in ('NCL','CL')", nativeQuery = true)
	int getclosedstatdisputByYear(int year);
	
	@Query(value ="SELECT count(*) from raise_despute where month(created_date) = ?1", nativeQuery = true)
	int getstatdisputByMonth(int month);
	
	int countByCreatedateGreaterThanEqualAndCreatedateLessThanEqual(Date value1, Date value2);

}
