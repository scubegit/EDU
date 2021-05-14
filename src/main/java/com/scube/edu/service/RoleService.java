package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.RoleMaster;
import com.scube.edu.response.RoleResponse;

public interface RoleService {

	RoleMaster getNameById(Long roleId);

	List<RoleResponse> getRoleList(HttpServletRequest request);

}
