package com.scube.edu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.StateEntity;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, Long> {
	
	@Query(value ="SELECT * from state_master where country_id =?1 and is_deleted = '?2'", nativeQuery = true)
	List<StateEntity> getStatebyCountryIdAndIsdeleted(Long id, String isdeleted);
	Optional<StateEntity> findById(Long id);
	
	StateEntity findByStateAndCountryId(String state,Long countryId);
	List<StateEntity> findAllByIsdeleted(String isdeleted);
	List<StateEntity> findByCountryIdAndIsdeleted(Long id, String isdeleted);
	StateEntity findByStateAndCountryIdAndIsdeleted(String state,Long CountryId,String isdeleted);

}
