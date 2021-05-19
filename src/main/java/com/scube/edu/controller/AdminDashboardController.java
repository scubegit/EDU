package com.scube.edu.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.FinancialStatResponse;
import com.scube.edu.response.TopFiverYearRevenueResponse;
import com.scube.edu.response.TopTenEmployResponse;
import com.scube.edu.response.VerifierPerformanceResponse;
import com.scube.edu.service.AdminDashboardService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/AdminDashboard")
public class AdminDashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);

	BaseResponse response = null;

	@Autowired
	AdminDashboardService adminDashboardService;
	@GetMapping(value = "/getRequestStatitics/{year}" )
	public ResponseEntity<BaseResponse> getRequestStatiticsByYearWeekMonth (@PathVariable int year) {
		
		System.out.println("*******AdminDashboardController getRequestStatiticsByYearWeekMonth********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	HashMap<String,HashMap<String,String>> List = adminDashboardService.getRequestStatByStatus(year);
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
	
	@GetMapping(value = "/getTopTenEmployer/{year}" )
	public ResponseEntity<BaseResponse> getTopTenemployersOfYear (@PathVariable int year) {
		
		System.out.println("*******AdminDashboardController getTopTenemployersOfYear********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	List<TopTenEmployResponse>  List = adminDashboardService.gettopTenEmployer(year);
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
	
	@GetMapping(value = "/getRequestratio/{year}" )
	public ResponseEntity<BaseResponse> requestRatio (@PathVariable int year) {
		
		System.out.println("*******AdminDashboardController requestRatio********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	Map<String,Integer>  List = adminDashboardService.getPostiveNegReqRation(year);
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
	
	@GetMapping(value = "/getYearRevenue" )
	public ResponseEntity<BaseResponse> getYearRevenue () {
		
		System.out.println("*******AdminDashboardController getYearRevenue********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	List<TopFiverYearRevenueResponse>  List = adminDashboardService.getTopFiveYearRevenue();
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
	
	@GetMapping(value = "/getDisputeRatio/{year}" )
	public ResponseEntity<BaseResponse> getDisputeRation (@PathVariable int year) {
		
		System.out.println("*******AdminDashboardController requestRatio********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	Map<String, Integer>  List = adminDashboardService.getDisputeRatio(year);
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
	

	@GetMapping(value = "/getFinancialStat/{fistofMont}/{currenDateOfmonth}" )
	public ResponseEntity<BaseResponse> getFinancialStat (@PathVariable String fistofMont,@PathVariable String currenDateOfmonth) {
		
		System.out.println("*******AdminDashboardController getFinancialStat********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	List<FinancialStatResponse> List = adminDashboardService.getFinancialStat(fistofMont, currenDateOfmonth);
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
	

	@GetMapping(value = "/getPerformanceGraphMonthly/{month}/{year}" )
	public ResponseEntity<BaseResponse> getMonthlyPerformanceGraph (@PathVariable int month,@PathVariable int year) {
		
		System.out.println("*******AdminDashboardController getPerformanceGraph********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	List<VerifierPerformanceResponse> List = adminDashboardService.getMonthlyPerformanceOfVerfier(month,year);
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
	
	@GetMapping(value = "/getPerformanceGraphDaily/{date}" )
	public ResponseEntity<BaseResponse> getDailyPerformanceGraph (@PathVariable String date) {
		
		System.out.println("*******AdminDashboardController getDailyPerformanceGraph********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	List<VerifierPerformanceResponse> List = adminDashboardService.getDailyPerformanceOfVerfier(date);
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
	
	@GetMapping(value = "/getPerformanceGraphCustomRange/{fromdate}/{todate}" )
	public ResponseEntity<BaseResponse> getPerformanceGraphCustomRange (@PathVariable String fromdate,@PathVariable String todate) {
		
		System.out.println("*******AdminDashboardController getPerformanceGraphCustomRange********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	List<VerifierPerformanceResponse> List = adminDashboardService.getPerformanceOfVerfier(fromdate, todate);
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
}


