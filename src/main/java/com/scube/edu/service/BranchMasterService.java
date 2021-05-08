package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.response.BranchResponse;

public interface BranchMasterService {
	
	public List<BranchResponse> getBranchList(Long id,HttpServletRequest request);

}
