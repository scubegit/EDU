package com.scube.edu.service;

import com.scube.edu.model.PassingYearMaster;

import com.scube.edu.model.UniversityStudentDocument;

public interface UniversityStudentDocService {
	
	UniversityStudentDocument getDocDataByEnrollmentNO(String  enrollNo);

}
