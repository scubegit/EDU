package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UserMasterEntity;
@Repository
public interface StreamRepository  extends JpaRepository<StreamMaster, Long>{

}
