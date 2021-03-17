package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.repository.DocumentRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.YearOfPassingResponse;

@Service
public class YearOfPassingServiceImpl  implements YearOfPassingService{
  
	 private static final Logger logger = LoggerFactory.getLogger(YearOfPassingServiceImpl.class);
		
		BaseResponse	baseResponse	= null;
		
		@Autowired
		YearOfPassingRepository yearOfPassingRespository;

		@Override
		public List<YearOfPassingResponse> getYearOfPassingList(HttpServletRequest request) {
			
			  List<YearOfPassingResponse> List = new ArrayList<>();
				
				List<PassingYearMaster> yearEntities    = yearOfPassingRespository.findAll();
				
				for(PassingYearMaster entity : yearEntities) {
					
					YearOfPassingResponse response = new YearOfPassingResponse();

					response.setId(entity.getId());
					response.setYearOfPassing(entity.getYearOfPassing());
					
					
					List.add(response);
				}
				
				return List;
		}
		
		@Override
		public PassingYearMaster getYearById(String id) {
				Long ID = Long.parseLong(id);
				System.out.println(ID);
				Optional<PassingYearMaster> yearEntity    = yearOfPassingRespository.findById(ID);
				PassingYearMaster yearEnt = yearEntity.get();
				System.out.println("yearEnt---"+ yearEnt);
				
						return yearEnt;
		}
}
