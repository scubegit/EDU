package com.scube.edu.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
	
	public UserResponse getUserInfoById(Long userId) {
			
			System.out.println("********UserServiceImpl getUserInfoById********"+ userId);
			
			UserResponse user = new UserResponse();
			
			Optional<UserMasterEntity> ent = userRepository.findById(userId);
			UserMasterEntity entt = ent.get();
			
			user.setEmail(entt.getEmailId());
			user.setPhone_no(entt.getPhoneNo());
			user.setId(entt.getId());
			user.setCompany_name(entt.getCompanyName());
			user.setContact_person_phone(entt.getContactPersonPhone());
			user.setCompany_email(entt.getCompanyEmailId());
			if(entt.getLastName() != null) {
				user.setName(entt.getFirstName() +" " + entt.getLastName());
			}else {
				user.setName(entt.getFirstName());
			}
			user.setContact_person_name(entt.getContactPersonName());
			user.setFirst_name(entt.getFirstName());
			user.setLast_name(entt.getLastName());
			
			return user;
		
		}

	
	


	

}
