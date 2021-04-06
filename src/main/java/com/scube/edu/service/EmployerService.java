package com.scube.edu.service;

import java.util.Date;
import java.util.List;

import com.scube.edu.response.EmployerVerificationDocResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;

public interface EmployerService {

	List<EmployerVerificationDocResponse> getOneMonthVerificationDocsDataByUserid(long userId, String fromDate,
			String toDate) throws Exception;

}
