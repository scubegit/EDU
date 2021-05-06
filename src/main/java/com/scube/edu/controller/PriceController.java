package com.scube.edu.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.PriceMaster;
import com.scube.edu.request.PriceAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.PriceMasterResponse;
import com.scube.edu.service.PriceService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/price")

public class PriceController {
	private static final Logger logger = LoggerFactory.getLogger(PriceController.class);

	BaseResponse response = null;

	@Autowired
	PriceService priceServices;

	
	  @GetMapping("/getPriceList") 
	  public ResponseEntity<Object> getPriceList(HttpServletRequest request) {
	  
	  response = new BaseResponse();
	  
	  try {
	  
	  logger.info("---------getPriceList controller----------------");
	  
	  
	  List<PriceMasterResponse> responseData = priceServices.getPriceList(request);
	  
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
	 

	// Abhishek Added

	@PostMapping("/priceAdd")
	public ResponseEntity<Object> addPrice(@RequestBody PriceAddRequest priceRequest, HttpServletRequest request) {

		logger.info("********PriceController addPrice()********");
		response = new BaseResponse();

		try {

			Boolean flag = priceServices.addPrice(priceRequest);

			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(flag);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			logger.error(e.getMessage());

			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());

			// return ResponseEntity.badRequest().body(response);
			return ResponseEntity.ok(response);
		}

	}
	
	@PostMapping("/priceDelete/{id}")
	public ResponseEntity<Object> deletePrice(@PathVariable Long id, HttpServletRequest request) {

		logger.info("********PriceController deletePrice()********");
		response = new BaseResponse();

		try {

			Boolean flag = priceServices.deletePrice(id);

			response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			response.setRespData(flag);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			logger.error(e.getMessage());

			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());

			// return ResponseEntity.badRequest().body(response);
			return ResponseEntity.ok(response);
		}

	}
	
	
	@PostMapping("/priceUpdate")
	public ResponseEntity<Object> updatePrice(@RequestBody PriceMaster priceMaster ,  HttpServletRequest request) {
		
		logger.info("********priceController updatePrice()********");
		response = new BaseResponse();
				
		
		
		try {
			
			response = priceServices.UpdatePrice(priceMaster);

			//Boolean flag = documentServices.addDocument(documentRequest);
			
			//response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
			//response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
			//response.setRespData(flag);
			
			return ResponseEntity.ok(response);
			
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			
			response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
			response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
			response.setRespData(e.getMessage());
			
			//return ResponseEntity.badRequest().body(response);
			return ResponseEntity.ok(response);
		}
		
   }
	
	//Abhishek Added

}