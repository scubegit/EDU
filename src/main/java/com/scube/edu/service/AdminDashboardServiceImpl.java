package com.scube.edu.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.controller.MasterController;
import com.scube.edu.model.RaiseDespute;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.AdminDashboardRepository;
import com.scube.edu.repository.RaiseDisputeRepository;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.response.FinancialStatResponse;
import com.scube.edu.response.TopFiverYearRevenueResponse;
import com.scube.edu.response.TopTenEmployResponse;
import com.scube.edu.response.VerifierPerformanceResponse;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	AdminDashboardRepository adminDashboardRepository;
	
	@Autowired
	RaiseDisputeRepository raiseDisputeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public HashMap<String,HashMap<String,Integer>> getRequestStatByStatus(int useryear) {
		
		logger.info("****AdminDashboardServiceImpl   getRequestStatByStatus******");
		
		Date d=new Date ();
		int day =d.getDay();
		int month=d.getMonth();
		month=month+1;
		int year=d.getYear(); 
		year=1900+year;
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(new Date());
		int today = c.get(Calendar.DAY_OF_WEEK);
		c.add(Calendar.DAY_OF_WEEK, -today+Calendar.MONDAY);
		
		Date datofmonday=c.getTime();
		System.out.println("MondayDate:- "+datofmonday);
		
        String dayWeekText = new SimpleDateFormat("EEEE").format(d);


		logger.info( "Date:-"  +d+ "year:-"  +year+ " month:-"+month+ "Day:-"+day+ " Day Name:-"+dayWeekText);
		
		HashMap<String,HashMap<String,Integer>> AllstatCountMap=new HashMap<>();

		HashMap<String,Integer> YearstatCountMap=new HashMap<>();
		HashMap<String,Integer> monthstatCountMap=new HashMap<>();
		HashMap<String,Integer> weekstatCountMap=new HashMap<>();

		int newreuestCount=adminDashboardRepository.getstatNewreqByYear(useryear);
		int closreuestCount=adminDashboardRepository.getstatclosreqByYear(useryear);
		int disputereqCount=raiseDisputeRepository.getstatdisputByYear(useryear);
	
		YearstatCountMap.put("New",newreuestCount);
		YearstatCountMap.put("Closed",closreuestCount);		
		YearstatCountMap.put("Dispute",disputereqCount);				
		
		if(useryear==year) {
		
		int newreuestMonthCount=adminDashboardRepository.getstatnewReqByMonth(month);
		int closreuestMonthCount=adminDashboardRepository.getstatclosreqByMonth(month);
		int disputereqMonthCount=raiseDisputeRepository.getstatdisputByYear(month);

				
		monthstatCountMap.put("New",newreuestMonthCount);
		monthstatCountMap.put("Closed",closreuestMonthCount);		
		monthstatCountMap.put("Dispute",disputereqMonthCount);				
		
		
		int newreuestWeekcount=adminDashboardRepository.countByCreatedateGreaterThanEqualAndCreatedateLessThanEqual(datofmonday,d);	
		int clsdreuestWeekcount=adminDashboardRepository.countByClosedDateGreaterThanEqualAndClosedDateLessThanEqual(datofmonday,d);
		int disputereqWeekCount=raiseDisputeRepository.countByCreatedateGreaterThanEqualAndCreatedateLessThanEqual(datofmonday,d);

		
		weekstatCountMap.put("New",newreuestWeekcount);
		weekstatCountMap.put("Closed",clsdreuestWeekcount);		
		weekstatCountMap.put("Dispute",disputereqWeekCount);	
		
		logger.info("Weekly ocunt = "+newreuestWeekcount);
		}
		
		AllstatCountMap.put("Year", YearstatCountMap);
		AllstatCountMap.put("CurrentMonth", monthstatCountMap);
		AllstatCountMap.put("CurrentWeek", weekstatCountMap);

		
		return AllstatCountMap;
		
	}

	@Override
	public List<TopTenEmployResponse>  gettopTenEmployer(int year) {
		logger.info("****AdminDashboardServiceImpl   gettopTenEmployer******");
		
		List<Object[]> toptemEmp=adminDashboardRepository.findVerificationRequestToptenEmp(year);
		List<TopTenEmployResponse> response=new ArrayList<>();
		for(Object[] list:toptemEmp )
			{				
			TopTenEmployResponse resp=new TopTenEmployResponse();
				int  amt=0;
				if(list[0]!=null) {
					amt=((BigDecimal) list[0]).intValue();
				}
				String compnyNm= (String) list[1];
				resp.setCompanyNm(compnyNm);
				resp.setTotalamt(amt);
				logger.info("amt=" +amt+ " compnayNm="+compnyNm);			
				response.add(resp);
		}
		return response;
	}

	@Override
	public Map<String, Integer> getPostiveNegReqRation(int year) {
		logger.info("****AdminDashboardServiceImpl   getPostiveNegReqRation******");
		Map<String,Integer> rationcount=new HashMap<>();
		int positiveReqCount=adminDashboardRepository.getcountOfpositiveReq(year);
		int negativeReqCount=adminDashboardRepository.getcountOfNegReq(year);

		rationcount.put("Positive", positiveReqCount);
		rationcount.put("Negative", negativeReqCount);

		return rationcount;
	}

	@Override
	public List<TopFiverYearRevenueResponse> getTopFiveYearRevenue() {
		logger.info("****AdminDashboardServiceImpl   getTopFiveYearRevenue******");
		List<Object[]> topFiyear=adminDashboardRepository.findVerificaTopFiveYearRevenu();
		List<TopFiverYearRevenueResponse> response=new ArrayList<>();

		for(Object[] list:topFiyear )
		{	
			TopFiverYearRevenueResponse resp=new TopFiverYearRevenueResponse();
			
			int  Totalamt= 0;
			if(list[0]!=null) {
				Totalamt=((BigDecimal) list[0]).intValue();
			}
			Integer year= (Integer) list[1];
			resp.setYear(Integer.toString(year));
			resp.setTotalAmt( Totalamt);
			logger.info("Totalamt=" +Totalamt+ " year="+year);			
			response.add(resp);
		
		}

		return response;
	}

	@Override
	public Map<String, Integer> getDisputeRatio(int year) {
		logger.info("****AdminDashboardServiceImpl   getDisputeRatio******");
		Map<String,Integer> rationcount=new HashMap<>();
		int raisedDisputCount=raiseDisputeRepository.getstatdisputByYear(year);
		int clearDisputCount=raiseDisputeRepository.getclosedstatdisputByYear(year);

		rationcount.put("Disputes", raisedDisputCount);
		rationcount.put("Clear", clearDisputCount);

		return rationcount;		
	}

	@Override
	public List<FinancialStatResponse> getFinancialStat(String fistofMont,String currenDateOfmonth) {
		logger.info("****AdminDashboardServiceImpl   getFinancialStat******");

		List<Object[]> financialStat=adminDashboardRepository.getFinanvialStat( fistofMont, currenDateOfmonth);
		List<FinancialStatResponse> response=new ArrayList<>();

		int stuUniamt=0;
		int studSecamt=0;
		for(Object[] list:financialStat )
		{	
			FinancialStatResponse resp=new FinancialStatResponse();
			int TotalUniamt=0;
			if(list[0]!=null) {
			TotalUniamt=((BigDecimal) list[0]).intValue();
			}
			int  TotalSecamt= 0;
			if(list[1]!=null) {
			TotalSecamt=((BigDecimal) list[1]).intValue();
			}
			String companyNm= (String) list[2];
			Long userId= ((BigInteger) list[3]).longValue();
			Long roleId=((BigInteger) list[4]).longValue();
			
			if(companyNm==null && roleId==1)
			{						
				stuUniamt=stuUniamt+TotalUniamt;
				studSecamt=studSecamt+TotalSecamt;
					
			}
			else {
			  resp.setCompnayNm(companyNm); 
			  resp.setUniversityamt(TotalUniamt);
			  resp.setSecureAmt(TotalSecamt);
			  resp.setUserId(userId);
			  resp.setRoleId(roleId);

			  logger.info("companyNm= " +companyNm+ " uniAmt=" +TotalUniamt+ " secAmt="+TotalSecamt);
			 			
			response.add(resp);
			}
		
		}
		FinancialStatResponse resp=new FinancialStatResponse();
		
		  resp.setCompnayNm("STUDENT"); 
		  resp.setUniversityamt(stuUniamt);
		  resp.setSecureAmt(studSecamt);
		 // resp.setUserId((long) 1);
		  resp.setRoleId((long) 1);

		  response.add(resp);


		return response;	
		
	}

	@Override
	public List<VerifierPerformanceResponse> getMonthlyPerformanceOfVerfier(int month,int year) {
		
		logger.info("****AdminDashboardServiceImpl   getMonthlyPerformanceOfVerfier******");
		
		List<Object[]> financialStat=adminDashboardRepository.getVerPerformanceMonthly(month,year);
		List<VerifierPerformanceResponse> response=new ArrayList<>();

		for(Object[] list:financialStat ) {
			
			VerifierPerformanceResponse resp=new VerifierPerformanceResponse();
			resp.setCount(((BigInteger) list[0]).intValue());
			resp.setVerId(((BigInteger)  list[1]).longValue());
			response.add(resp);
		}

		

		return response;
	}

	@Override
	public List<VerifierPerformanceResponse> getDailyPerformanceOfVerfier(String str) throws ParseException {
		logger.info("****AdminDashboardServiceImpl   getDailyPerformanceOfVerfier******");
		
		//Date newdate=Date.valueOf(str);
		
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(str);
		List<Object[]> financialStat=adminDashboardRepository.getVerPerformanceDaily(date1);
		List<VerifierPerformanceResponse> response=new ArrayList<>();

		for(Object[] list:financialStat ) {
			
			VerifierPerformanceResponse resp=new VerifierPerformanceResponse();
			resp.setCount(((BigInteger) list[0]).intValue());
			resp.setVerId(((BigInteger)  list[1]).longValue());
			response.add(resp);
		}		return null;
	}


}
