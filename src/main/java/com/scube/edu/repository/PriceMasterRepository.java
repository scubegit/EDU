package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PriceMaster;


public interface PriceMasterRepository extends JpaRepository<PriceMaster, Long>{

	@Query(value = "select * from pricemaster where (select (?1 - year_of_passing) as diff from passing_year_master where id = ?2) between year_range_start and year_range_end", nativeQuery = true)
	PriceMaster getPriceByYearDiff(int year, Long yearOfPassId);

	
	PriceMaster findById(long id);

	

}
