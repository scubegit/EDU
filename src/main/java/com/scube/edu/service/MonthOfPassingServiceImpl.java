package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.MonthOfPassing;
import com.scube.edu.repository.MonthOfPassingRepository;
import com.scube.edu.response.MonthOfPassingResponse;

@Service
public class MonthOfPassingServiceImpl implements MonthOfPassingService {

	@Autowired
	MonthOfPassingRepository monthOfPassingRepository;
	
	@Override
	public List<MonthOfPassingResponse> getAllMonth() {
    
		List<MonthOfPassing>list=monthOfPassingRepository.findAll();
		
		List<MonthOfPassingResponse>response=new ArrayList<>();
		for(MonthOfPassing monthlist:list) {
			MonthOfPassingResponse resp=new MonthOfPassingResponse();
			resp.setId(monthlist.getId());
			resp.setMonthOfPAssing(monthlist.getMonthOfPassing());
			response.add(resp);
		}
		return response;
	}

}
