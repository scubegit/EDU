package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.RoleMaster;

@Repository
public interface RoleRepository extends JpaRepository<RoleMaster, Long>{
	
	

}
