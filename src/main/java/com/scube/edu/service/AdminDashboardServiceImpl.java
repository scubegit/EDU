package com.scube.edu.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.controller.MasterController;
import com.scube.edu.model.RaiseDespute;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.AdminDashboardRepository;
import com.scube.edu.repository.RaiseDisputeRepository;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);

	@Autowired
	AdminDashboardRepository adminDashboardRepository;
	
	@Autowired
	RaiseDisputeRepository raiseDisputeRepository;
	
	@Override
	public HashMap<String,HashMap<String,Integer>> getRequestStatByStatus() {
		
		logger.info("****AdminDashboardServiceImpl   getRequestStatByStatus******");
		
		Date d=new Date ();
		int day =d.getDay();
		int month=d.getMonth();
		month=month+1;
		int year=d.getYear(); 
		year=1900+year;
        String dayWeekText = new SimpleDateFormat("EEEE").format(d);


		logger.info( "Date:-"  +d+ "year:-"  +year+ " month:-"+month+ "Day:-"+day+ " Day Name:-"+dayWeekText);
		
		int yearlynewrquest=0;
		int yearlyclosedRqest=0;
		int yearlyDisputeRqest=0;
		HashMap<String,HashMap<String,Integer>> AllstatCountMap=new HashMap<>();

		HashMap<String,Integer> YearstatCountMap=new HashMap<>();
		HashMap<String,Integer> monthstatCountMap=new HashMap<>();

		List<VerificationRequest> reuestList=adminDashboardRepository.getstatByYear(year);
		if(reuestList!=null) {
		for(VerificationRequest request:reuestList)
		{
				yearlynewrquest++;
				
			String status=request.getDocStatus();
			if(!status.equals("Requested"))
					{
				if(request.getClosedDate()!=null) {
				Date clsdate=request.getClosedDate();
				int clsdtYear=clsdate.getYear();
				clsdtYear=1900+clsdtYear;
				logger.info("clsdtYear"+clsdtYear);
				if(clsdtYear==year) {
				yearlyclosedRqest++;
				}
					}
				RaiseDespute disputecheck=raiseDisputeRepository.findByApplicationId(request.getApplicationId());
				if(disputecheck!=null) {
					if(disputecheck.getCreatedate()!=null) {
					Date diputcrtddate=disputecheck.getCreatedate();
					int diputcrtdtYear=diputcrtddate.getYear();
					diputcrtdtYear=1900+diputcrtdtYear;
					logger.info("diputcrtdtYear"+diputcrtdtYear);
					if(diputcrtdtYear==year) {
				 if(disputecheck.getStatus()!=null&&disputecheck.getStatus().equals("1"))
					 {
					 yearlyDisputeRqest++;
					 }
					}
				}
				}
			}
		}
		YearstatCountMap.put("New",yearlynewrquest);
		YearstatCountMap.put("Closed",yearlyclosedRqest);		
		YearstatCountMap.put("Dispute",yearlyDisputeRqest);				
		}
		int monthlynewrquest=0;
		int monthlyclosedRqest=0;
		int monthlyDisputeRqest=0;
		List<VerificationRequest> reuestMonthList=adminDashboardRepository.getstatByMonth(month);
		if(reuestMonthList!=null) {
		for(VerificationRequest request:reuestMonthList)
		{
			
				monthlynewrquest++;
					
		String status=request.getDocStatus();
		if(!status.equals("Requested"))
				{
			if(request.getClosedDate()!=null) {
			Date clsdate=request.getClosedDate();
			int clsdtmonth=clsdate.getMonth();
			clsdtmonth=clsdtmonth+1;
			logger.info("clsdtmonth"+clsdtmonth);
			if(clsdtmonth==month) {
				monthlyclosedRqest++;
			}
			}
				RaiseDespute disputecheck=raiseDisputeRepository.findByApplicationId(request.getApplicationId());
				if(disputecheck!=null) {
					if(disputecheck.getCreatedate()!=null) {
					Date diputcrtddate=disputecheck.getCreatedate();
					int diputcrtdmonth=diputcrtddate.getMonth();
					diputcrtdmonth=diputcrtdmonth+1;
					if(diputcrtdmonth==month) {
				 if(disputecheck.getStatus()!=null&&disputecheck.getStatus().equals("1"))
					 {
					 monthlyDisputeRqest++;
					 }
					}
					}
				}
			}
		}
		monthstatCountMap.put("New",monthlynewrquest);
		monthstatCountMap.put("Closed",monthlyclosedRqest);		
		monthstatCountMap.put("Dispute",monthlyDisputeRqest);				
		}
		
		logger.info("count"+monthlynewrquest );
		AllstatCountMap.put("Current Year", YearstatCountMap);
		AllstatCountMap.put("Current Month", monthstatCountMap);

		
		return AllstatCountMap;
		
	}


}
