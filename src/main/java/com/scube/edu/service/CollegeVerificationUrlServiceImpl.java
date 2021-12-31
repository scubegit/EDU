package com.scube.edu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.CollegeVerificationUrlEntity;
import com.scube.edu.repository.CollegeVerificationUrlRepository;
import com.scube.edu.response.BaseResponse;

@Service
public class CollegeVerificationUrlServiceImpl implements CollegeVerificationUrlService{
	
private static final Logger logger = LoggerFactory.getLogger(CollegeVerificationUrlServiceImpl.class);
	
	BaseResponse	baseResponse	= null;
	
	@Autowired
	CollegeVerificationUrlRepository collegeVerificationUrlRepo;

	@Override
	public CollegeVerificationUrlEntity getByRandomKeyAndChangeStatus(String randomKey) {
		
		logger.info("*****CollegeVerificationUrlServiceImpl getByUrlAndChangeStatus*****"+ randomKey);
		
		CollegeVerificationUrlEntity urlEnt = collegeVerificationUrlRepo.findByRandomKey(randomKey);
		urlEnt.setStatus("Disabled");
		
		collegeVerificationUrlRepo.save(urlEnt);
		
		return urlEnt;
	}

	@Override
	public CollegeVerificationUrlEntity getEntityByRandomKey(String random) {
		
		CollegeVerificationUrlEntity urlEnt = collegeVerificationUrlRepo.findByRandomKey(random);
		
		return urlEnt;
	}

}
