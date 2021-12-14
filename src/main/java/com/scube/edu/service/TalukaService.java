package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.TalukaEntity;
import com.scube.edu.response.TalukaResponse;

public interface TalukaService {
	public List<TalukaResponse> getTalukaList(Long id,HttpServletRequest request);
	public TalukaEntity getTalukaById(Long id);
	public TalukaEntity getTalukaIdByNm(String taluka,Long DistrictId );

	/*
	 * boolean saveSem(TalukaRequest stateReq, HttpServletRequest request) throws
	 * Exception; public boolean deleteTaluka(Long id, HttpServletRequest request)
	 * throws Exception; public boolean updateTaluka(TalukaRequest stateReq,
	 * HttpServletRequest request) throws Exception;
	 */
	public List<TalukaResponse> getAllTalukaList(HttpServletRequest request);


}
