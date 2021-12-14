package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.StateEntity;
import com.scube.edu.request.StateRequest;
import com.scube.edu.response.StateResponse;

public interface StateService {
	public List<StateResponse> getStateList(Long id,HttpServletRequest request);
	public StateEntity getStateById(Long id);
	public StateEntity getStateIdByNm(String state,Long CountryId );

	/*
	 * boolean saveSem(StateRequest stateReq, HttpServletRequest request) throws
	 * Exception; public boolean deleteState(Long id, HttpServletRequest request)
	 * throws Exception; public boolean updateState(StateRequest stateReq,
	 * HttpServletRequest request) throws Exception;
	 */
	public List<StateResponse> getAllStateList(HttpServletRequest request);


}
