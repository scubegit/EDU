package com.scube.edu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.UserMasterEntity;


@Repository
public interface UserRepository extends JpaRepository<UserMasterEntity, Long>{

	  UserMasterEntity findByUsername(String username);

	  boolean existsByUsernameAndIsactive(String username, String isactive);
	
	  
	  UserMasterEntity findByEmailId(String emailid);

	  boolean existsByEmailIdAndIsactive(String emailId, String string);
	
	  @Query(value = "SELECT TIMESTAMPDIFF(minute,um.created_date,now() ) as miutes from user_master as um where um.id= ?1", nativeQuery = true)
	  int gethoursToValidateTheLink(Long id);

	//List<UserMasterEntity> getAll();
	//boolean existsByAndIsdeletedAndIsactive(String username, String IsDeleted);

	}

