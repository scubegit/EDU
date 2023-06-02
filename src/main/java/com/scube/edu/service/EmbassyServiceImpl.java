package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.scube.edu.model.EmbassyEntity;
import com.scube.edu.repository.EmbassyRepository;
import com.scube.edu.response.EmbassyResponse;

@Service
public class EmbassyServiceImpl implements EmbassyService{
	
	@Autowired
	EmbassyRepository embassyrepository;

	@Override
	public List<EmbassyResponse> getembassy() {
		// TODO Auto-generated method stub
		
		System.out.println("Hello");
		List<EmbassyResponse> List = new ArrayList<>();
		
		List<EmbassyEntity> embassyEntities    = embassyrepository.findAllByIsdeleted("N");
		
		for(EmbassyEntity entity : embassyEntities) {
			
			EmbassyResponse embassyResponse = new EmbassyResponse();

			embassyResponse.setId(entity.getId());
			embassyResponse.setUniversityId(entity.getUniversityId());
			embassyResponse.setEmbassyName(entity.getEmbassyName());
			
			List.add(embassyResponse);
		}
		return List;
	}

	@Override
	public List<EmbassyResponse> getembassyList(Long id, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		System.out.println("Hello"+id);
		List<EmbassyResponse> List = new ArrayList<>();
		
		List<EmbassyEntity> embassyEntities    = embassyrepository.findByIdAndIsdeleted(id, "N");
		
		for(EmbassyEntity entity : embassyEntities) {
			
			EmbassyResponse embassyResponse = new EmbassyResponse();

			embassyResponse.setId(entity.getId());
			embassyResponse.setUniversityId(entity.getUniversityId());
			embassyResponse.setEmbassyName(entity.getEmbassyName());
			
			List.add(embassyResponse);
		}
		return List;
	}
}
