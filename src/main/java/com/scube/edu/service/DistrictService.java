package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.DistrictEntity;
import com.scube.edu.response.DistrictResponse;

public interface DistrictService {
	public List<DistrictResponse> getDistrictList(Long id,HttpServletRequest request);
	public DistrictEntity getDistrictById(Long id);
	public DistrictEntity getDistrictIdByNm(String state,Long CountryId );

	/*
	 * boolean saveSem(DistrictRequest stateReq, HttpServletRequest request) throws
	 * Exception; public boolean deleteDistrict(Long id, HttpServletRequest request)
	 * throws Exception; public boolean updateDistrict(DistrictRequest stateReq,
	 * HttpServletRequest request) throws Exception;
	 */
	public List<DistrictResponse> getAllDistrictList(HttpServletRequest request);


}
