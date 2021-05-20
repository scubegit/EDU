package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.RoleWiseTabAcessEntity;

@Repository
public interface RoleWiseAccessTabRepository extends JpaRepository<RoleWiseTabAcessEntity, Long>{

}
