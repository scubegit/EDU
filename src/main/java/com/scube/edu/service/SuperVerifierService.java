package com.scube.edu.service;

import java.util.List;

import com.scube.edu.response.EmployerVerificationDocResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.VerificationResponse;

public interface SuperVerifierService {

	List<VerificationResponse> getVerificationDocList(String fromDate, String toDate);

	List<StudentVerificationDocsResponse> setStatusForSuperVerifierDocument(Long id, String status);

}
