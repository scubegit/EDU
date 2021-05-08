package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.response.BranchResponse;
import com.scube.edu.response.SemesterResponse;

public interface SemesterService {
	public List<SemesterResponse> getSemList(Long id,HttpServletRequest request);
	public SemesterEntity getSemById(Long id);

}
