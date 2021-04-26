package com.scube.edu.service;

import java.util.List;

import com.scube.edu.request.StatusChangeRequest;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.UniversityVerifierResponse;

public interface UniversityVerifierService {
	
	public List<UniversityVerifierResponse> getUniversityVerifierRequestList() throws Exception;

	public List<StudentVerificationDocsResponse> setStatusForUniversityDocument(
			StatusChangeRequest statusChangeRequest);


}
