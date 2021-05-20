package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.RoleWiseTabAcessEntity;
import com.scube.edu.repository.RoleWiseAccessTabRepository;
import com.scube.edu.response.RoleWiseTabAccessResponse;

@Service
public class RoleWiseTabAccessServiceImpl implements RoleWiseTabAccessService {

	
	private static final Logger logger = LoggerFactory.getLogger(RoleWiseTabAccessServiceImpl.class);

	@Autowired
	RoleWiseAccessTabRepository roleWiseAccessTabRepository;
	
	@Override
	public List<RoleWiseTabAccessResponse> getAllTabs(Long roleId) {
		
		 List<RoleWiseTabAccessResponse> branchList = new ArrayList<>();
			
			List<RoleWiseTabAcessEntity> branchEntities =roleWiseAccessTabRepository.findByRoleid(roleId) ;
			
			for(RoleWiseTabAcessEntity entity : branchEntities) {
				
				RoleWiseTabAccessResponse response = new RoleWiseTabAccessResponse();

				response.setId(entity.getId());
				response.setTabName(entity.getTabName());
				response.setRoleId(entity.getRoleid());
				
				
				branchList.add(response);
			}
			
			return branchList;	}
	
	

}
