package com.scube.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PriceMaster;

@Repository
public interface PriceMasterRepository extends JpaRepository<PriceMaster, Long>{

	@Query(value = "select * from pricemaster where (select (?1 - year_of_passing) as diff from passing_year_master where id = ?2) between year_range_start and year_range_end", nativeQuery = true)
//	@Query(value = "select * from pricemaster where 20 between year_range_start and year_range_end", nativeQuery = true)
//	@Query(value = "select * from pricemaster where id=1", nativeQuery = true)
	PriceMaster getPriceByYearDiff(int year, Long yearOfPassId);

	
	PriceMaster findById(long id);

	

}
