package com.scube.edu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.model.SemesterEntity;

@Repository
public interface SemesterRepository extends JpaRepository<SemesterEntity, Long> {
	
	@Query(value ="SELECT * from semester_Master where stream_id =?1", nativeQuery = true)
	List<SemesterEntity> getSembyStreamId(Long id);
	Optional<SemesterEntity> findById(Long id);
	
	SemesterEntity findBySemester(String sem);
}
