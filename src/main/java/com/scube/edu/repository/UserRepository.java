package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.UserMasterEntity;

@Repository
public interface UserRepository extends JpaRepository<UserMasterEntity, Long>{

	UserMasterEntity findByUsername(String username);

	boolean existsByUsernameAndIsactive(String username, String isactive);
	
	

	//List<UserMasterEntity> getAll();
	//boolean existsByAndIsdeletedAndIsactive(String username, String IsDeleted);

	}

