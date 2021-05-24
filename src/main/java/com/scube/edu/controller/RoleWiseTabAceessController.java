package com.scube.edu.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.RoleWiseTabAccessResponse;
import com.scube.edu.service.AdminDashboardService;
import com.scube.edu.service.RoleWiseTabAccessService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/DashBoardAccess")
public class RoleWiseTabAceessController {

	private static final Logger logger = LoggerFactory.getLogger(RoleWiseTabAceessController.class);

	BaseResponse response = null;

	@Autowired
	RoleWiseTabAccessService roleWiseTabAccessService;
	
	@GetMapping(value = "/getAllTabs/{roleId}" )
	public ResponseEntity<BaseResponse> getDashBoadAccessofRoles (@PathVariable Long roleId) {
		
		System.out.println("*******AdminDashboardController getRequestStatiticsByYearWeekMonth********");
		
		response = new BaseResponse();
		
	    try {
	    	
	    	List<RoleWiseTabAccessResponse> List = roleWiseTabAccessService.getAllTabs(roleId);
				
				response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
				response.setRespData(List);
				
				return ResponseEntity.ok(response);
					
			}catch (Exception e) {
				
				logger.error(e.getMessage()); //BAD creds message comes from here
				
				response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
				response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
				response.setRespData(e.getMessage());
				
				return ResponseEntity.badRequest().body(response);
				
			}
	}
}
