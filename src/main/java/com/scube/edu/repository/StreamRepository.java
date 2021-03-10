package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UserMasterEntity;

public interface StreamRepository  extends JpaRepository<StreamMaster, Long>{

}
