package com.scube.edu.repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.VerificationRequest;
import com.scube.edu.response.TopTenEmployResponse;

@Repository
public interface AdminDashboardRepository extends JpaRepository<VerificationRequest, Long>{

	@Query(value ="SELECT count(*) from verification_request where year(created_date) = ?1", nativeQuery = true)
	String getstatNewreqByYear(int year);
	
	@Query(value ="SELECT count(*) from verification_request where year(closed_date) = ?1", nativeQuery = true)
	String getstatclosreqByYear(int year);
	
	
	@Query(value ="SELECT count(*) from verification_request where month(created_date) = ?1 and year(created_date)=year(sysdate())", nativeQuery = true)
	String getstatnewReqByMonth(int month);
	
	@Query(value ="SELECT count(*) from verification_request where month(closed_date) = ?1 and year(created_date)=year(sysdate())", nativeQuery = true)
	String getstatclosreqByMonth(int month);
	
	@Query(value ="SELECT count(*) from verification_request where date(created_date) >= ?1 and date(created_date) <=?2 ", nativeQuery = true)
	String countByCreatedateGreaterThanEqualAndCreatedateLessThanEqual(String value1, String value2);
	
	@Query(value ="SELECT count(*) from verification_request where date(closed_date) >= ?1 and date(closed_date) <=?2 ", nativeQuery = true)
	String countByClosedDateGreaterThanEqualAndClosedDateLessThanEqual(String value1, String value2);

	@Query(value="select sum(vr.doc_amt_with_gst) as totalAmt ,um.company_name,vr.user_id from verification_request vr left join user_master um on vr.user_id=um.id where um.company_name is not null and um.role_id =2 and year(vr.created_date)= ?1 group by user_id order by totalAmt desc limit 10",nativeQuery = true)
	List<Object[]> findVerificationRequestToptenEmp(int year);
	
	@Query(value ="SELECT count(*) from verification_request where year(closed_date) = ?1 and doc_status in ('UN_Approved_Pass','Uni_Auto_Approved_Pass','SVD_Approved_Pass','UN_Approved_Fail','Uni_Auto_Approved_Fail','SVD_Approved_Fail')", nativeQuery = true)
	String getcountOfpositiveReq(int year);

	@Query(value ="SELECT count(*) from verification_request where year(closed_date) = ?1 and doc_status in ('UN_Rejected','Uni_Auto_Rejected','SVD_Rejected')", nativeQuery = true)
	String getcountOfNegReq(int year);
	
	@Query(value="select sum(vr.doc_amt_with_gst) as docamtwithgst ,year(created_date) as topyear from verification_request vr group by topyear order by docamtwithgst desc limit 5",nativeQuery = true)
	List<Object[]> findVerificaTopFiveYearRevenu();
	
	@Query(value="SELECT sum(vr.doc_uni_amt) ,sum(vr.doc_secur_charge) ,um.company_name as companyname,vr.user_id as userid,um.role_id from verification_request vr left join user_master um on vr.user_id=um.id where date(vr.created_date )>= ?1 and date(vr.created_date) <= ?2 group by vr.user_id ",nativeQuery = true)
	List<Object[]> getFinanvialStat(String fistofMont,String currenDateOfmonth);
	
	@Query(value="SELECT count(*),ver_id,um.first_name,um.last_name FROM verification_request vr left join user_master um on um.id=vr.ver_id  where month(ver_action_date) = ?1 and  year(ver_action_date) = ?2 and ver_id is not null   group by ver_id",nativeQuery = true)
	List<Object[]> getVerPerformanceMonthly(int month,int year);

	@Query(value="SELECT count(*),ver_id,um.first_name,um.last_name FROM verification_request vr left join user_master um on um.id=vr.ver_id  where date(ver_action_date)= ?1  and ver_id is not null   group by ver_id",nativeQuery = true)
	List<Object[]> getVerPerformanceDaily(String date);
	
	@Query(value="SELECT count(vr.ver_id),vr.ver_id,um.first_name,um.last_name FROM verification_request vr left join user_master um on um.id=vr.ver_id where date(ver_action_date)>=?1 and date(ver_action_date)<=?2 and vr.ver_id is not null   group by vr.ver_id",nativeQuery = true)
	List<Object[]> getVerPerformance(String fromdate,String todate);

	@Query(value="SELECT MONTHNAME(created_date) as month,SUM(CASE WHEN"
			+ " vr.doc_status ='UN_Approved_Pass' || vr.doc_status ='UN_Approved_Fail' || vr.doc_status ='SVD_Approved_Pass' ||"
			+ " vr.doc_status ='SVD_Approved_Fail' || vr.doc_status ='SVD_Rejected' || vr.doc_status ='UN_Rejected'"
			+ " THEN 1  ELSE 0 END) AS closed ,"
			+ " SUM(CASE WHEN vr.doc_status ='Requested' || vr.doc_status ='Approved_Pass' || vr.doc_status ='Offline' || vr.doc_status ='Approved_Fail' ||"
			+ " vr.doc_status ='Unable To Verify' || vr.doc_status ='Ver_Request_Edit' || vr.doc_status ='Uni_Request_Edit' ||"
			+ " vr.doc_status ='Approved_Pass' THEN 1  ELSE 0 END) AS pending from verification_request vr where "
			+ " YEAR(created_date)=(?1)  GROUP BY MONTH(created_date) , YEAR(created_date) ;",nativeQuery = true)
	List<Map<String ,Object>> getGeneralSummary(int year);

	@Query(value="SELECT MONTHNAME(created_date) as month,SUM(CASE WHEN DATEDIFF(now(), created_date) < 31"
			+ " THEN 1  ELSE 0 END) AS lessthan ,SUM(CASE WHEN DATEDIFF(now(), created_date) > 30 and DATEDIFF(now(), created_date) < 60"
			+ " THEN 1  ELSE 0 END) AS inbetween, SUM(CASE WHEN DATEDIFF(now(), created_date) > 60 THEN 1 ELSE 0"
			+ " END) AS above from verification_request vr where YEAR(created_date)= (?1) and doc_status not in ('Offline','Unable To Verify',"
			+ " 'UN_Approved_Pass','UN_Approved_Fail','SVD_Approved_Pass','SVD_Approved_Fail','SVD_Rejected','UN_Rejected') "
			+ "GROUP BY MONTH(created_date) , YEAR(created_date) ;", nativeQuery = true)
	List<Map<String, Object>> getAgeingSummary(int year);

	@Query(value="select ow.month, ow.Pending as pending, cw.Closed as closed from closed_view cw left join open_view ow on cw.month = ow.month " + 
			" where ow.year = (?1) and cw.year = (?1)",nativeQuery = true)
	List<Map<String, Object>> getNormalSummary(int year);

	
}
