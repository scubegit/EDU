package com.scube.edu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scube.edu.model.RequestTypeMaster;
import com.scube.edu.response.DocumentResponse;
import com.scube.edu.response.RequestTypeResponse;

public interface RequestTypeService {

	List<RequestTypeResponse> getRequestTypeList(HttpServletRequest request);

	RequestTypeResponse getNameById(Long requestType);

}
