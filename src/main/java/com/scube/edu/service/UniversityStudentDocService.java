package com.scube.edu.service;

import com.scube.edu.model.PassingYearMaster;

import com.scube.edu.model.UniversityStudentDocument;

public interface UniversityStudentDocService {
	
	
	
	UniversityStudentDocument getDocDataBySixFields(String  enrollNo, String firstName, String lastName, Long streamId, String yearOfPassing, Long collegeId);

}
