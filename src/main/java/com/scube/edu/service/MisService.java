package com.scube.edu.service;

import java.util.List;

import com.scube.edu.response.MisResponse;

public interface MisService {

	List<MisResponse> getMisList(String fromDate, String toDate);

}
