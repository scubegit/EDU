package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scube.edu.model.UserMasterEntity;

public interface YearOfPassingRepository extends JpaRepository<UserMasterEntity, Long> {

}
