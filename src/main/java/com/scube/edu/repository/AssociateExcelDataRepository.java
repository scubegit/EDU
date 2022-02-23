package com.scube.edu.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.AssociateExcelDataEntity;

@Repository
public interface AssociateExcelDataRepository extends JpaRepository<AssociateExcelDataEntity, Long> {
	
	@Query(value="Call aws_excel_compare()", nativeQuery = true)
	List<Map<String, Object>> excelCompare();
	
	
	@Query(value = "DELETE FROM associate_excel_data", nativeQuery = true)
	void daleteExcelData();

	/*
	 * @Query(value = "DELETE FROM associate_excel_data_test", nativeQuery = true)
	 * Integer daleteProcedureData();
	 */

}