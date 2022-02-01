package com.scube.edu.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scube.edu.response.AgeingSummaryResponse;
import com.scube.edu.response.FinancialStatResponse;
import com.scube.edu.response.GeneralSummaryResponse;
import com.scube.edu.response.TopFiverYearRevenueResponse;
import com.scube.edu.response.TopTenEmployResponse;
import com.scube.edu.response.VerifierPerformanceResponse;


public interface AdminDashboardService {

	public HashMap<String,HashMap<String,String>> getRequestStatByStatus(int year) throws Exception;
	
	public List<TopTenEmployResponse>  gettopTenEmployer(int year);
	
	public Map<String,Integer> getPostiveNegReqRation(int year);

	public List<TopFiverYearRevenueResponse> getTopFiveYearRevenue();

	public Map<String,Integer> getDisputeRatio(int year);

	public List<FinancialStatResponse>  getFinancialStat(String fistofMont,String currenDateOfmonth)throws ParseException ;
	
	public List<VerifierPerformanceResponse> getMonthlyPerformanceOfVerfier(int month,int year);
	
	public List<VerifierPerformanceResponse> getDailyPerformanceOfVerfier(String date) ;

	public List<VerifierPerformanceResponse> getPerformanceOfVerfier(String frmdate,String todate);

	public List<GeneralSummaryResponse> getRequestStatusSummaryByYear(int year);

	public List<AgeingSummaryResponse> getAgeingSummaryByYear(int year);
	
}
