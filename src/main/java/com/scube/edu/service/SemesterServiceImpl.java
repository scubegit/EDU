package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.repository.BranchMasterRepository;
import com.scube.edu.repository.SemesterRepository;
import com.scube.edu.request.SemesterRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.BranchResponse;
import com.scube.edu.response.SemesterResponse;

@Service
public class SemesterServiceImpl implements SemesterService{
	private static final Logger logger = LoggerFactory.getLogger(CollegeSeviceImpl.class);

	BaseResponse	baseResponse	= null;

    @Autowired
    SemesterRepository semesterRepository;
	
	@Override
	public List<SemesterResponse> getSemList(Long id, HttpServletRequest request) {
		logger.info("ID"+id);
		 List<SemesterResponse> branchList = new ArrayList<>();
			
			List<SemesterEntity> branchEntities    = semesterRepository.getSembyStreamId(id);
			for(SemesterEntity entity : branchEntities) {
				
				SemesterResponse response = new SemesterResponse();

				response.setId(entity.getId());
				response.setSemester(entity.getSemester());
				response.setStreamId(entity.getStreamId());
				response.setUniversityId(entity.getUniversityId());
				
				branchList.add(response);
			}
			return branchList;
	}

	@Override
	public SemesterEntity getSemById(Long id) {
		System.out.println(id);
		Optional<SemesterEntity> semEntity    = semesterRepository.findById(id);
		SemesterEntity branchEnt = semEntity.get();
		System.out.println("yearEnt---"+ branchEnt);
		
				return branchEnt;
}
	@Override
	public SemesterEntity getSemIdByNm(String sem,Long StreamId ) {
		System.out.println(sem);
		SemesterEntity semEntity = semesterRepository.findBySemesterAndStreamId(sem,StreamId);
		System.out.println("yearEnt---"+ semEntity);
		
				return semEntity;
}

	@Override
	public boolean saveSem(SemesterRequest semReq, HttpServletRequest request) throws Exception {
		
		logger.info("*******SemesterServiceImpl addSem*******");
		
		SemesterEntity sEnt = semesterRepository.findBySemesterAndStreamId(semReq.getSemestername(), semReq.getStreamid());
		
		if(sEnt != null) {
			throw new Exception("This sem name and Stream Id combo already exist.");
		}else {
			SemesterEntity semEnt = new SemesterEntity();
			
			semEnt.setIsdeleted("N");
			semEnt.setSemester(semReq.getSemestername());
			semEnt.setStreamId(semReq.getStreamid());
			semEnt.setUniversityId(semReq.getUniversityid());
			
			semesterRepository.save(semEnt);
			return true;
		}
		
		
	}
}
