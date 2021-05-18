package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.YearlyVerReqBackup;

@Repository
public interface SaveYearDataRepository extends JpaRepository<YearlyVerReqBackup, Long>{
	

	@Query(value="select year(vr.created_date) as financial_year,sum(vr.doc_amt_with_gst) as total_Amt, count(*) as new_req, (select count(*) from verification_request where year(closed_date)=financial_year) as closed_req,(select count(*) from verification_request where doc_status in ('UN_Approved','Uni_Auto_Approved','SVD_Approved') and year(closed_date)=financial_year) as  positive_req,(select count(*)from edu_db.verification_request where  doc_status in ('UN_Rejected','Uni_Auto_Rejected','SVD_Rejected') and year(closed_date)=financial_year) as  Negative_Req,(SELECT count(*) from raise_despute where year(created_date) =financial_year) as dispute_raised, (SELECT count(*) from raise_despute where year(created_date) = financial_year and status in ('NCL','CL')) as clear_dispute from verification_request vr where year(vr.created_date)<=(year(sysdate())-2) group by financial_year"  ,nativeQuery = true)
	List<YearlyVerReqBackup> findprevYearData();

}
