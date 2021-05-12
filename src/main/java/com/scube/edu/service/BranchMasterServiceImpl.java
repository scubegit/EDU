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
import com.scube.edu.model.CollegeMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.repository.BranchMasterRepository;
import com.scube.edu.request.BranchRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.BranchResponse;
import com.scube.edu.response.CollegeResponse;

@Service
public class BranchMasterServiceImpl implements BranchMasterService {
	
private static final Logger logger = LoggerFactory.getLogger(CollegeSeviceImpl.class);
	
	BaseResponse	baseResponse	= null;

    @Autowired
	BranchMasterRepository branchMasterRepository;
	
	@Override
	public List<BranchResponse> getBranchList(Long id,HttpServletRequest request) {
		logger.info("ID"+id);
		 List<BranchResponse> branchList = new ArrayList<>();
			
			List<BranchMasterEntity> branchEntities    = branchMasterRepository.getbrachbyStreamIdAndIsdeleted(id, "N");
			for(BranchMasterEntity entity : branchEntities) {
				
				BranchResponse response = new BranchResponse();

				response.setId(entity.getId());
				response.setBranchName(entity.getBranchName());
				response.setStreamId(entity.getStreamId());
				response.setUniversityId(entity.getUniversityId());
				
				branchList.add(response);
			}
			return branchList;
	}

	@Override
	public BranchMasterEntity getbranchById(Long id) {
		System.out.println(id);
		Optional<BranchMasterEntity> branchEntity    = branchMasterRepository.findById(id);
		BranchMasterEntity semEnt = branchEntity.get();
		System.out.println("yearEnt---"+ semEnt);
		
				return semEnt;
	}
	@Override
	public BranchMasterEntity getbranchIdByname(String brnchnm,Long strmId) {
		System.out.println(brnchnm);
		BranchMasterEntity branchEntity    = branchMasterRepository.findByBranchNameAndStreamId(brnchnm,strmId);
		System.out.println("yearEnt---"+ branchEntity);
		
				return branchEntity;
	}

	@Override
	public boolean saveBranch(BranchRequest branchReq, HttpServletRequest request) throws Exception {
		
		logger.info("*******BranchMasterServiceImpl saveBranch*******"+ branchReq.getBranchname());
		
		BranchMasterEntity bme = branchMasterRepository.findByBranchNameAndStreamId(branchReq.getBranchname(), branchReq.getStreamid());
		
		if(bme != null) {
			
			throw new Exception("The Branch and streamId combo already exist.");
			
		}else {
			logger.info("add new branch");
			
			BranchMasterEntity bmEnt = new BranchMasterEntity();
			
			bmEnt.setBranchName(branchReq.getBranchname());
			bmEnt.setIsdeleted("N");
			bmEnt.setStreamId(branchReq.getStreamid());
			bmEnt.setUniversityId(branchReq.getUniversityid());
			
			branchMasterRepository.save(bmEnt);
			return true;
			
		}
	}

	@Override
	public boolean deleteBranch(Long id, HttpServletRequest request) {
		
		Optional<BranchMasterEntity> bme = branchMasterRepository.findById(id);
		BranchMasterEntity bm = bme.get();
		
		bm.setIsdeleted("Y");
		
		branchMasterRepository.save(bm);
		
		return true;
	}

	@Override
	public boolean updateBranch(BranchRequest branchReq, HttpServletRequest request) {
		
		logger.info("*******BranchMasterServiceImpl updateBranch*******"+ branchReq.getBranchname());
		
		Optional<BranchMasterEntity> bme = branchMasterRepository.findById(branchReq.getId());
		BranchMasterEntity bm = bme.get();
		
		bm.setBranchName(branchReq.getBranchname());
		bm.setStreamId(branchReq.getStreamid());
		bm.setUniversityId(branchReq.getUniversityid());
		
		branchMasterRepository.save(bm);
		
		return true;
	}

	@Override
	public List<BranchResponse> getAllBranchList(HttpServletRequest request) {
		
		logger.info("*******BranchMasterServiceImpl getAllBranchList*******");
		
		List<BranchMasterEntity> list = branchMasterRepository.findByIsdeleted("N");
		
		List<BranchResponse> resp = new ArrayList<>();
		
		for(BranchMasterEntity bme: list) {
			
			BranchResponse br = new BranchResponse();
			
			br.setBranchName(bme.getBranchName());
			br.setId(bme.getId());
			br.setStreamId(bme.getStreamId());
			br.setUniversityId(bme.getUniversityId());
			
			resp.add(br);
			
		}
		
		return resp;
	}
}
