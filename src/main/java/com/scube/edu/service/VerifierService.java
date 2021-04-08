package com.scube.edu.service;

import java.util.List;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.VerificationResponse;


public interface VerifierService {

	public List<VerificationResponse> getVerifierRequestList() throws Exception;

	public List<StudentVerificationDocsResponse> verifyDocument(Long id);

	public List<StudentVerificationDocsResponse> setStatusForVerifierDocument(Long id, String status, Long verifiedBy);



}
