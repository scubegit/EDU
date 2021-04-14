package com.scube.edu.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.model.UniversityStudentDocument;

public interface AssociateManagerService {
	
	public String saveStudentInfo(MultipartFile excelfile,MultipartFile datafile) throws IOException;
	
	//UniversityStudentDocument  getStudentDataByenrollmentNo(String enrollmentNo);


}
