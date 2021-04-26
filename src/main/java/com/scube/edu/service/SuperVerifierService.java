package com.scube.edu.service;

import java.util.List;

import com.scube.edu.request.StatusChangeRequest;
import com.scube.edu.response.DisputeResponse;
import com.scube.edu.response.EmployerVerificationDocResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.VerificationResponse;

public interface SuperVerifierService {

	List<VerificationResponse> getVerificationDocList(String fromDate, String toDate);

	List<StudentVerificationDocsResponse> setStatusForSuperVerifierDocument(StatusChangeRequest statusChangeRequest) throws Exception;

	List<DisputeResponse> getDisputeList();

	VerificationResponse getVerificationRequestDetails(long verification_id);

}
