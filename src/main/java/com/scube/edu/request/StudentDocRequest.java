package com.scube.edu.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter

public class StudentDocRequest {
	
	private String firstName; 
	private String lastName;
	private Long streamId;
	private String docName;
	private String enrollNo;
	private Long yearOfPassId;
	private Long collegeNameId;
	private Long applicationId;
	private Long uniId;
	private Long userId;
	private String requestTypeId;
	private Long verReqId;
	private String uploadDocPath;
	
//	private MultipartFile file; 

}
