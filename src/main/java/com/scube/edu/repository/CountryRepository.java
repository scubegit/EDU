package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.CountryMaster;
@Repository
public interface CountryRepository  extends JpaRepository<CountryMaster, Long>{

	
	CountryMaster findById(long pid);

	CountryMaster deleteById(long id);
	CountryMaster findByCountryName( String Countryname);
	List<CountryMaster> findAllByIsdeleted(String isdeleted);
	CountryMaster findByCountryNameAndIsdeleted( String Countryname,String isdeleted);

	
}
