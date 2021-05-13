package com.scube.edu.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scube.edu.response.FinancialStatResponse;
import com.scube.edu.response.TopFiverYearRevenueResponse;
import com.scube.edu.response.TopTenEmployResponse;


public interface AdminDashboardService {

	public HashMap<String,HashMap<String,Integer>> getRequestStatByStatus(int year);
	
	public List<TopTenEmployResponse>  gettopTenEmployer(int year);
	
	public Map<String,Integer> getPostiveNegReqRation(int year);

	public List<TopFiverYearRevenueResponse> getTopFiveYearRevenue();

	public Map<String,Integer> getDisputeRatio(int year);

	public List<FinancialStatResponse>  getFinancialStat(String fistofMont,String currenDateOfmonth);

}
