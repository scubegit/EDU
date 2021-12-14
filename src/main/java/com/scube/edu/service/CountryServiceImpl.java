package com.scube.edu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.CountryMaster;
import com.scube.edu.model.CountryMaster;
import com.scube.edu.repository.DocumentRepository;
import com.scube.edu.repository.CountryRepository;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.request.CountryRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CountryResponse;
import com.scube.edu.util.StringsUtils;

@Service
public class CountryServiceImpl  implements CountryService{
	
	private static final Logger logger = LoggerFactory.getLogger(CountryServiceImpl.class);
		
		BaseResponse	baseResponse	= null;
		
		@Autowired
		CountryRepository  countryRespository;

		@Override
		public List<CountryResponse> getCountryList(HttpServletRequest request) {

			List<CountryResponse> List = new ArrayList<>();
			
			List<CountryMaster> countryEntities    = countryRespository.findAllByIsdeleted("N");
			
			for(CountryMaster entity : countryEntities) {
				
				CountryResponse countryResponse = new CountryResponse();

				countryResponse.setId(entity.getId());
				countryResponse.setCountryName(entity.getCountryName());
				
				List.add(countryResponse);
			}
			return List;
		}
		
		@Override
		public CountryMaster getNameById(Long countryId) {
			
			System.out.println("*******countryServiceImpl getNameById*******");
			
			Optional<CountryMaster> country = countryRespository.findById(countryId);
			CountryMaster stre = country.get();
			
			return stre;
		}
		
}
