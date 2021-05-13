package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.RoleMaster;
import com.scube.edu.repository.RoleRepository;
import com.scube.edu.response.RoleResponse;

@Service
public class RoleServiceImpl implements RoleService{
	
	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public RoleMaster getNameById(Long roleId) {
		
		logger.info("*******RoleServiceImpl getNameById*******");
		
		Optional<RoleMaster> roleRecord = roleRepository.findById(roleId);
		RoleMaster roleMaster = roleRecord.get();
		
		return roleMaster;
	}

	@Override
	public List<RoleResponse> getRoleList(HttpServletRequest request) {
		
		logger.info("*******RoleServiceImpl getRoleList*******");
		
		List<RoleMaster> list = roleRepository.findAll();
		List<RoleResponse> respList = new ArrayList<>();
		
		for(RoleMaster role:list) {
			
			RoleResponse resp = new RoleResponse();
			
			resp.setId(role.getId());
			resp.setRolename(role.getRoleName());
			
			respList.add(resp);
			
		}
		
		return respList;
	}
	
}
