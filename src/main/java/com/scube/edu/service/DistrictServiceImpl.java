package com.scube.edu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.DistrictEntity;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.model.StateEntity;
import com.scube.edu.repository.DistrictRepository;
import com.scube.edu.request.DistrictRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.SemesterResponse;
import com.scube.edu.response.DistrictResponse;

@Service
public class DistrictServiceImpl implements DistrictService{
	private static final Logger logger = LoggerFactory.getLogger(DistrictServiceImpl.class);

	BaseResponse	baseResponse	= null;

    @Autowired
    DistrictRepository districtRepository;
    
    @Autowired
    StateService stateService;
	
	@Override
	public List<DistrictResponse> getDistrictList(Long id, HttpServletRequest request) {
		logger.info("ID"+id);
		 List<DistrictResponse> districtList = new ArrayList<>();
			
			List<DistrictEntity> districtEntities    = districtRepository.findByStateIdAndIsdeleted(id,"N");
			for(DistrictEntity entity : districtEntities) {
				
				DistrictResponse response = new DistrictResponse();

				response.setId(entity.getId());
				response.setDistrict(entity.getDistrict());
				response.setStateId(entity.getStateId());
				
				districtList.add(response);
			}
			return districtList;
	}
	
	  @Override public DistrictEntity getDistrictById(Long id) { System.out.println(id);
	  Optional<DistrictEntity> districtEntity = districtRepository.findById(id);
	  DistrictEntity districtEnt = districtEntity.get();
	  System.out.println("districtEnt---"+ districtEnt);
	  
	  return districtEnt; }
	  
	  @Override public DistrictEntity getDistrictIdByNm(String district,Long StateId ) {
	  System.out.println(district); DistrictEntity semEntity =
	  districtRepository.findByDistrictAndStateId(district,StateId);
	  System.out.println("semEnt---"+ semEntity);
	  
	  return semEntity; }

  
  
  
	  @Override
		public List<DistrictResponse> getAllDistrictList(HttpServletRequest request) {
			
			logger.info("*******SemesterServiceImpl getAllSemList*******");
			
			List<DistrictEntity> list = districtRepository.findAllByIsdeleted("N");
			
			List<DistrictResponse> respList = new ArrayList<>();
			
			for(DistrictEntity semEnt: list) {
				
				DistrictResponse sem = new DistrictResponse();
				
				StateEntity state = stateService.getStateById(semEnt.getStateId());
				
				sem.setId(semEnt.getId());
				sem.setDistrict(semEnt.getDistrict());
				sem.setStateId(semEnt.getStateId());
				sem.setStateName(state.getState());
				
				respList.add(sem);
				
			}
			
			return respList;
		}
	  
	  
	  
	  
}