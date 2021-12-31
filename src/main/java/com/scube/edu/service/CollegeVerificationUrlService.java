package com.scube.edu.service;

import com.scube.edu.model.CollegeVerificationUrlEntity;

public interface CollegeVerificationUrlService {

	CollegeVerificationUrlEntity getByRandomKeyAndChangeStatus(String randomKey);

	CollegeVerificationUrlEntity getEntityByRandomKey(String random);

}
