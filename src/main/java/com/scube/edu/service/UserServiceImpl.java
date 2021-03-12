package com.scube.edu.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.UserResponse;
import com.scube.edu.util.StringsUtils;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public List<UserResponse> getUserList() {
		
		List<UserResponse> userList = new ArrayList<>();
		
		List<UserMasterEntity> userEntities    = userRepository.findAll();
		
		for(UserMasterEntity entity : userEntities) {
			
			UserResponse userResponse = new UserResponse();

			userResponse.setId(1);
			userResponse.setCompany_name(entity.getCompanyName());
			userResponse.setEmail(entity.getEmailId());
			userResponse.setGstno(entity.getGSTNo());
			
			userList.add(userResponse);
		}
		
		return userList;
	
	}

	
	


	

}
