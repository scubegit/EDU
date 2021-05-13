package com.scube.edu.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scube.edu.model.VerificationRequest;
import com.scube.edu.response.TopTenEmployResponse;

@Repository
public interface AdminDashboardRepository extends JpaRepository<VerificationRequest, Long>{

	@Query(value ="SELECT count(*) from verification_request where year(created_date) = ?1", nativeQuery = true)
	int getstatNewreqByYear(int year);
	
	@Query(value ="SELECT count(*) from verification_request where year(closed_date) = ?1", nativeQuery = true)
	int getstatclosreqByYear(int year);
	
	
	@Query(value ="SELECT count(*) from verification_request where month(created_date) = ?1", nativeQuery = true)
	int getstatnewReqByMonth(int month);
	
	@Query(value ="SELECT count(*) from verification_request where year(closed_date) = ?1", nativeQuery = true)
	int getstatclosreqByMonth(int month);
	
	int countByCreatedateGreaterThanEqualAndCreatedateLessThanEqual(Date value1, Date value2);
	
	int countByClosedDateGreaterThanEqualAndClosedDateLessThanEqual(Date value1, Date value2);

	@Query(value="select sum(vr.doc_amt_with_gst) as totalAmt ,um.company_name,vr.user_id from verification_request vr left join user_master um on vr.user_id=um.id where um.company_name is not null and um.role_id =2 and year(vr.created_date)= ?1 group by user_id order by totalAmt desc limit 10",nativeQuery = true)
	List<Object[]> findVerificationRequestToptenEmp(int year);
	
	@Query(value ="SELECT count(*) from verification_request where year(closed_date) = ?1 and doc_status in ('UN_Approved','Uni_Auto_Approved','SVD_Approved')", nativeQuery = true)
	int getcountOfpositiveReq(int year);

	@Query(value ="SELECT count(*) from verification_request where year(closed_date) = ?1 and doc_status in ('UN_Rejected','Uni_Auto_Rejected','SVD_Rejected')", nativeQuery = true)
	int getcountOfNegReq(int year);
	
	@Query(value="select sum(vr.doc_amt_with_gst) as docamtwithgst ,year(created_date) as topyear from verification_request vr group by topyear order by docamtwithgst desc limit 5",nativeQuery = true)
	List<Object[]> findVerificaTopFiveYearRevenu();
	
	@Query(value="SELECT sum(vr.doc_uni_amt) ,sum(vr.doc_secur_charge) ,um.company_name as companyname,vr.user_id as userid,um.role_id from edu_db.verification_request vr left join edu_db.user_master um on vr.user_id=um.id where vr.created_date>= ?1 and vr.created_date<= ?2 group by vr.user_id ",nativeQuery = true)
	List<Object[]> getFinanvialStat(String fistofMont,String currenDateOfmonth);
}
