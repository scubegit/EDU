package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.request.BranchRequest;
import com.scube.edu.response.BranchResponse;

public interface BranchMasterService {
	
	public List<BranchResponse> getBranchList(Long id,HttpServletRequest request);
	public BranchMasterEntity getbranchById(Long id);
	public BranchMasterEntity getbranchIdByname(String brnchnm,Long strmId);
	public boolean saveBranch(BranchRequest branchReq, HttpServletRequest request) throws Exception;
	public boolean deleteBranch(Long id, HttpServletRequest request);
	public boolean updateBranch(BranchRequest branchReq, HttpServletRequest request);
	public List<BranchResponse> getAllBranchList(HttpServletRequest request);

}
