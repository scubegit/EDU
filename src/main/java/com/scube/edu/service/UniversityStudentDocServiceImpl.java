package com.scube.edu.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.repository.UniversityStudentDocRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.YearOfPassingResponse;

@Service
public class UniversityStudentDocServiceImpl implements UniversityStudentDocService{

	
	private static final Logger logger = LoggerFactory.getLogger(UniversityStudentDocServiceImpl.class);
	
	BaseResponse	baseResponse	= null;
	
	@Autowired
	UniversityStudentDocRepository  studentDocumentRepository;

	
	@Override
	public UniversityStudentDocument getDocDataByEnrollmentNO(String  enrollNo) {
	
			
				Optional<UniversityStudentDocument> docEntity    = studentDocumentRepository.findByEnrollmentNo(enrollNo);
				
				UniversityStudentDocument stuEnt = docEntity.get();
				
				System.out.println("-----Entities---"+stuEnt);
				
		         return stuEnt;
		         
		         
		}
	
	

	public UniversityStudentDocument getUniversityDocDataById(Long id) {
	
			
				Optional<UniversityStudentDocument> docEntity    = studentDocumentRepository.findById(id);
				
				UniversityStudentDocument universtyStudentData = docEntity.get();
				
				System.out.println("-----Entities---"+universtyStudentData);
				
		         return universtyStudentData;
		         
		         
		}
	
}
