package com.scube.edu.service;

import java.util.List;

import com.scube.edu.response.StudentVerificationDocsResponse;

public interface VerifierService {

	public List<StudentVerificationDocsResponse> getVerifierRequestList() throws Exception;

}
