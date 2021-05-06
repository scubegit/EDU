package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PriceMaster;
import com.scube.edu.request.PriceAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.DocumentResponse;
import com.scube.edu.response.PriceMasterResponse;

public interface PriceService {
	
	
	List<PriceMasterResponse> getPriceList(HttpServletRequest request);
	
	//Abhishek Added
		public Boolean addPrice(PriceAddRequest priceRequest) throws Exception;
		
		public BaseResponse UpdatePrice(PriceMaster priceMaster) throws Exception;
		//Abhishek Added

		Boolean deletePrice(Long id);

}
