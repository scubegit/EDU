package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.UserMasterEntity;

public interface DocumentRepository extends JpaRepository<DocumentMaster, Long>{

}
