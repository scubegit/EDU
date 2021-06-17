package com.scube.edu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.model.checkautoReadingActiveEntity;

@Repository
public interface ChkAutoReadingStatus extends JpaRepository<checkautoReadingActiveEntity, Long> {
	
public Optional<checkautoReadingActiveEntity> findById(Long id);

@Modifying(clearAutomatically = true)
@Transactional
@Query(value = "UPDATE auto_reading_excel_chk  set flag=?1 where id=1", nativeQuery = true)
Integer updateFlg(String flag);

}
