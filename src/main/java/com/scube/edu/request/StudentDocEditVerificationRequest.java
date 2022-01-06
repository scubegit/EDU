package com.scube.edu.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter

public class StudentDocEditVerificationRequest {
	
	private long id;
	private String firstname; 
	private String lastname;
	private Long streamid;
	private String docname;
	private String enrollno;
	private Long yearofpassid;
	private Long collegenameid;
//	private String requesttypeid;
	private String uploaddocpath;
	private Long branchId;
	private Long semId;
	private String altEmail;
	
	private String monthOfPassing;



}
