package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.request.SemesterRequest;
import com.scube.edu.response.BranchResponse;
import com.scube.edu.response.SemesterResponse;

public interface SemesterService {
	public List<SemesterResponse> getSemList(Long id,HttpServletRequest request);
	public SemesterEntity getSemById(Long id);
	public SemesterEntity getSemIdByNm(String sem,Long StreamId );
	boolean saveSem(SemesterRequest semReq, HttpServletRequest request) throws Exception;
	public boolean deleteSemester(Long id, HttpServletRequest request);
	public boolean updateSem(SemesterRequest semReq, HttpServletRequest request);
	public List<SemesterResponse> getAllSemList(HttpServletRequest request);


}
