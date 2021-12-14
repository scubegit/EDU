package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.DistrictEntity;
import com.scube.edu.model.TalukaEntity;
import com.scube.edu.repository.TalukaRepository;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.TalukaResponse;

@Service
public class TalukaServiceImpl implements TalukaService{
	private static final Logger logger = LoggerFactory.getLogger(TalukaServiceImpl.class);

	BaseResponse	baseResponse	= null;

    @Autowired
    TalukaRepository talukaRepository;
    
    @Autowired
    DistrictService districtService;
	
	@Override
	public List<TalukaResponse> getTalukaList(Long id, HttpServletRequest request) {
		logger.info("ID"+id);
		 List<TalukaResponse> talukaList = new ArrayList<>();
			
			List<TalukaEntity> talukaEntities    = talukaRepository.findByDistrictIdAndIsdeleted(id,"N");
			for(TalukaEntity entity : talukaEntities) {
				
				TalukaResponse response = new TalukaResponse();

				response.setId(entity.getId());
				response.setTaluka(entity.getTaluka());
				response.setDistrictId(entity.getDistrictId());
				
				talukaList.add(response);
			}
			return talukaList;
	}
	
	  @Override public TalukaEntity getTalukaById(Long id) { System.out.println(id);
	  Optional<TalukaEntity> talukaEntity = talukaRepository.findById(id);
	  TalukaEntity talukaEnt = talukaEntity.get();
	  System.out.println("talukaEnt---"+ talukaEnt);
	  
	  return talukaEnt; }
	  
	  @Override public TalukaEntity getTalukaIdByNm(String taluka,Long DistrictId ) {
	  System.out.println(taluka); TalukaEntity talEntity = talukaRepository.findByTalukaAndDistrictId(taluka,DistrictId);
	  System.out.println("talukaEnt---"+ talEntity);
	  
	  return talEntity; }

  
  
  
	  @Override
		public List<TalukaResponse> getAllTalukaList(HttpServletRequest request) {
			
			logger.info("*******SemesterServiceImpl getAllSemList*******");
			
			List<TalukaEntity> list = talukaRepository.findAllByIsdeleted("N");
			
			List<TalukaResponse> respList = new ArrayList<>();
			
			for(TalukaEntity talukaEnt: list) {
				
				TalukaResponse sem = new TalukaResponse();
				
				DistrictEntity district = districtService.getDistrictById(talukaEnt.getDistrictId());
				
				sem.setId(talukaEnt.getId());
				sem.setTaluka(talukaEnt.getTaluka());
				sem.setDistrictId(talukaEnt.getDistrictId());
				sem.setDistrictName(district.getDistrict());
				
				respList.add(sem);
				
			}
			
			return respList;
		}
	  
	  
	  
	  
}