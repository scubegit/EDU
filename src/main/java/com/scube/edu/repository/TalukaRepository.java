package com.scube.edu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.TalukaEntity;

@Repository
public interface TalukaRepository extends JpaRepository<TalukaEntity, Long> {
	
	@Query(value ="SELECT * from taluka_master where district_id =?1 and is_deleted = '?2'", nativeQuery = true)
	List<TalukaEntity> getTalukabyDistrictIdAndIsdeleted(Long id, String isdeleted);
	Optional<TalukaEntity> findById(Long id);
	
	TalukaEntity findByTalukaAndDistrictId(String taluka,Long districtId);
	List<TalukaEntity> findAllByIsdeleted(String isdeleted);
	List<TalukaEntity> findByDistrictIdAndIsdeleted(Long id, String isdeleted);
	TalukaEntity findByTalukaAndDistrictIdAndIsdeleted(String taluka,Long DistrictId,String isdeleted);

}
