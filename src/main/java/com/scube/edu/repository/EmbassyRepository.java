package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.EmbassyEntity;

@Repository
public interface EmbassyRepository  extends JpaRepository<EmbassyEntity, Long>{
	
	List<EmbassyEntity> findAllByIsdeleted(String deleted);
	List<EmbassyEntity> findByIdAndIsdeleted(Long id, String deleted);

}
