package com.scube.edu.service;

import java.util.List;

import com.scube.edu.response.UniversityVerifierResponse;
import com.scube.edu.response.VerificationResponse;

public interface UniversityVerifierService {
	
	public List<UniversityVerifierResponse> getUniversityVerifierRequestList() throws Exception;


}
