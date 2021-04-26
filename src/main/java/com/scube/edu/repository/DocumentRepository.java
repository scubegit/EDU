package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UserMasterEntity;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentMaster, Long>{
	
	DocumentMaster findById(long id);
	
	DocumentMaster deleteById(long id);

}
