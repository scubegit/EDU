package com.scube.edu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.DistrictEntity;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
	
	@Query(value ="SELECT * from district_master where state_id =?1 and is_deleted = '?2'", nativeQuery = true)
	List<DistrictEntity> getDistrictbyStateIdAndIsdeleted(Long id, String isdeleted);
	Optional<DistrictEntity> findById(Long id);
	
	DistrictEntity findByDistrictAndStateId(String district,Long stateId);
	List<DistrictEntity> findAllByIsdeleted(String isdeleted);
	List<DistrictEntity> findByStateIdAndIsdeleted(Long id, String isdeleted);
	DistrictEntity findByDistrictAndStateIdAndIsdeleted(String district,Long StateId,String isdeleted);

}
