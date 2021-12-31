package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scube.edu.model.CollegeVerificationUrlEntity;

public interface CollegeVerificationUrlRepository extends JpaRepository<CollegeVerificationUrlEntity, Long>{

	@Query(value = "select MAX(id) from college_verification_url_entity", nativeQuery = true)
	Long findMaxId();

	CollegeVerificationUrlEntity findByUrl(String url);

	CollegeVerificationUrlEntity findByRandomKey(String random);

}
