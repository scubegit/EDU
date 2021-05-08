package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.BranchMasterEntity;

@Repository
public interface BranchMasterRepository extends JpaRepository<BranchMasterEntity, Long> {

	@Query(value ="SELECT * from branch_Master where stream_id =?1", nativeQuery = true)
	List<BranchMasterEntity> getbrachbyStreamId(Long id);
}
