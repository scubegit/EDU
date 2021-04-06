package com.scube.edu.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;

import com.scube.edu.response.StudentVerificationDocsResponse;

@Repository
public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long>{

	@Query(value = "SELECT pym.year_of_passing as yearr, vr.* FROM edu_db.verification_request vr left join edu_db.passing_year_master pym on vr.year_of_passing_id = pym.id where user_id = ?1", nativeQuery = true)
	List<VerificationRequest> findByUserId(long userId);

	@Query(value = "SELECT * FROM verification_request where doc_status = 'Requested' and assigned_to = 0 order by created_date asc limit 5", nativeQuery = true)
	List<VerificationRequest> getVerifierRecords();

	@Query(value = "SELECT * FROM verification_request where doc_status != 'Requested' and user_id = ?1" , nativeQuery = true)
	List<VerificationRequest> findByStatusAndUserId(long userId);


	List<VerificationRequest> findByApplicationId(long applicationId);

	@Query(value = "SELECT MAX(application_id) from verification_request " , nativeQuery = true)
	Long getMaxApplicationId();

	@Query(value = "SELECT * FROM verification_request where id = (?1)" , nativeQuery = true)
	List<VerificationRequest> getDataByIdToVerify(long id);
	
	VerificationRequest findById(long id);

	@Query(value = "SELECT * FROM verification_request where user_id = (?1) and created_date >= (?2) and created_date <= (?3)" , nativeQuery = true)
	List<VerificationRequest> findByUserIdAndDates(long userId, String fromDate, String toDate);
	

}
