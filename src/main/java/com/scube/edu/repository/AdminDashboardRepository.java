package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.VerificationRequest;

@Repository
public interface AdminDashboardRepository extends JpaRepository<VerificationRequest, Long>{

	@Query(value ="SELECT * from verification_request where year(created_date) = ?1", nativeQuery = true)
	List<VerificationRequest> getstatByYear(int year);

	@Query(value ="SELECT * from verification_request where month(created_date) = ?1", nativeQuery = true)
	List<VerificationRequest> getstatByMonth(int month);
	
}
