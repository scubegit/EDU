package com.scube.edu.service;

import java.util.Date;
import java.util.List;

import com.scube.edu.response.EmployerVerificationDocResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.VerificationResponse;

public interface EmployerService {

	List<VerificationResponse> getOneMonthVerificationDocsDataByUserid(long userId, String fromDate,
			String toDate) throws Exception;

}
