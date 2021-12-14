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

import com.scube.edu.model.StateEntity;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.CountryMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.repository.StateRepository;
import com.scube.edu.request.StateRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.SemesterResponse;
import com.scube.edu.response.StateResponse;

@Service
public class StateServiceImpl implements StateService{
	private static final Logger logger = LoggerFactory.getLogger(StateServiceImpl.class);

	BaseResponse	baseResponse	= null;

    @Autowired
    StateRepository stateRepository;
    
    @Autowired
    CountryService countryService;
	
	@Override
	public List<StateResponse> getStateList(Long id, HttpServletRequest request) {
		logger.info("ID"+id);
		 List<StateResponse> stateList = new ArrayList<>();
			
			List<StateEntity> stateEntities    = stateRepository.findByCountryIdAndIsdeleted(id,"N");
			for(StateEntity entity : stateEntities) {
				
				StateResponse response = new StateResponse();

				response.setId(entity.getId());
				response.setState(entity.getState());
				response.setCountryId(entity.getCountryId());
				
				stateList.add(response);
			}
			return stateList;
	}
	
	  @Override public StateEntity getStateById(Long id) { System.out.println(id);
	  Optional<StateEntity> stateEntity = stateRepository.findById(id);
	  StateEntity stateEnt = stateEntity.get();
	  System.out.println("stateEnt---"+ stateEnt);
	  
	  return stateEnt; }
	  
	  @Override public StateEntity getStateIdByNm(String state,Long CountryId ) {
	  System.out.println(state); StateEntity semEntity =
	  stateRepository.findByStateAndCountryId(state,CountryId);
	  System.out.println("semEnt---"+ semEntity);
	  
	  return semEntity; }

  
  
  
	  @Override
		public List<StateResponse> getAllStateList(HttpServletRequest request) {
			
			logger.info("*******SemesterServiceImpl getAllSemList*******");
			
			List<StateEntity> list = stateRepository.findAllByIsdeleted("N");
			
			List<StateResponse> respList = new ArrayList<>();
			
			for(StateEntity semEnt: list) {
				
				StateResponse sem = new StateResponse();
				
				CountryMaster stream = countryService.getNameById(semEnt.getCountryId());
				
				sem.setId(semEnt.getId());
				sem.setState(semEnt.getState());
				sem.setCountryId(semEnt.getCountryId());
				//sem.setCountryName(stream.getCountryName());
				
				respList.add(sem);
				
			}
			
			return respList;
		}
	  
	  
	  
	  
}