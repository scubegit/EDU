package com.scube.edu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.MonthOfPassingResponse;
import com.scube.edu.response.PriceMasterResponse;
import com.scube.edu.service.MonthOfPassingService;
import com.scube.edu.service.PriceService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/monthofpassing")
public class MonthOfPassingController {
	private static final Logger logger = LoggerFactory.getLogger(PriceController.class);

	BaseResponse response = null;

	@Autowired
	MonthOfPassingService monthOfPassingService;

	
	  @GetMapping("/getMonthList") 
	  public ResponseEntity<Object> getMonthList(HttpServletRequest request) {
	  
	  response = new BaseResponse();
	  
	  try {
	  
	  logger.info("--------- MonthOfPassingController getPriceList ----------------");
	  
	  
	  List<MonthOfPassingResponse> responseData = monthOfPassingService.getAllMonth();
	  
	  response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
	  response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
	  response.setRespData(responseData);
	  
	  
	  
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
