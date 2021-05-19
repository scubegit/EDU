package com.scube.edu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.YearlyVerReqBackup;

@Repository
public interface YearlyVerReqBackupRepository extends JpaRepository<YearlyVerReqBackup, Long>{
	

	@Query(value="select vr.id,year(vr.created_date) as financial_year,sum(vr.doc_amt_with_gst) as total_Amt,sum(vr.doc_uni_amt) as university_amt , sum(vr.doc_secur_charge) as secure_amt, count(*) as new_request, (select count(*) from verification_request where year(closed_date)=financial_year) as closed_request,(select count(*) from verification_request where doc_status in ('UN_Approved','Uni_Auto_Approved','SVD_Approved') and year(closed_date)=financial_year) as  positive_request,(select count(*)from edu_db.verification_request where  doc_status in ('UN_Rejected','Uni_Auto_Rejected','SVD_Rejected') and year(closed_date)=financial_year) as  negative_request,(SELECT count(*) from raise_despute where year(created_date) =financial_year) as dispute_raised, (SELECT count(*) from raise_despute where year(created_date) = financial_year and status in ('NCL','CL')) as dispute_clear from verification_request vr where year(vr.created_date)<=(year(sysdate())-2) group by financial_year"  ,nativeQuery = true)
	List<YearlyVerReqBackup> findprevYearData();
	
	@Query(value ="SELECT new_request from yearly_ver_req_backup where financial_year = ?1", nativeQuery = true)
	int getstatNewreqByYear(int year);
	
	@Query(value ="SELECT closed_request from yearly_ver_req_backup where financial_year = ?1", nativeQuery = true)
	int getstatclosreqByYear(int year);
	
	@Query(value ="SELECT dispute_raised from yearly_ver_req_backup where financial_year = ?1", nativeQuery = true)
	int getstatdisputByYear(int year);
	
	
	
	@Query(value ="SELECT positive_request from yearly_ver_req_backup where financial_year = ?1", nativeQuery = true)
	int getcountOfpositiveReq(int year);

	@Query(value ="SELECT negative_request from yearly_ver_req_backup where financial_year = ?1 ", nativeQuery = true)
	int getcountOfNegReq(int year);
	
	@Query(value="select total_amt as docamtwithgst ,financial_year as topyear from yearly_ver_req_backup  group by topyear order by docamtwithgst desc limit 5",nativeQuery = true)
	List<Object[]> findVerificaTopFiveYearRevenu();
	
	@Query(value ="SELECT dispute_clear from yearly_ver_req_backup where financial_year = ?1", nativeQuery = true)
	int getclosedstatdisputByYear(int year);
}
