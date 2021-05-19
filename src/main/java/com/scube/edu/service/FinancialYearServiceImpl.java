package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.FinancialYearEntity;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.repository.FinancialYearRepository;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.FinancialStatResponse;
import com.scube.edu.response.FinancialYearResponse;
import com.scube.edu.response.StreamResponse;

@Service
public class FinancialYearServiceImpl implements FinancialYearService{

private static final Logger logger = LoggerFactory.getLogger(MasterServiceImpl.class);
	
	BaseResponse	baseResponse	= null;
	
	@Autowired
	FinancialYearRepository financialYearRepository;
	
	@Override
	public List<FinancialYearResponse> getFinancialYearList() {

		
				List<FinancialYearResponse> List = new ArrayList<>();
				
				List<FinancialYearEntity> Entities    = financialYearRepository.findAll();
				
				for(FinancialYearEntity entity : Entities) {
					
					FinancialYearResponse streamResponse = new FinancialYearResponse();

					streamResponse.setId(entity.getId());
					streamResponse.setFinYear(entity.getFinancialYear());
					
					List.add(streamResponse);
				}
				return List;
			
		
	}



	
	

}
