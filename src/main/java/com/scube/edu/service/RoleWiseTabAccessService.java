package com.scube.edu.service;

import java.util.List;

import com.scube.edu.response.RoleWiseTabAccessResponse;

public interface RoleWiseTabAccessService {
	
	
	public List<RoleWiseTabAccessResponse> getAllTabs(Long roleId); 

}
