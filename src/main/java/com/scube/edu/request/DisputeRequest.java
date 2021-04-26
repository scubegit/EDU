package com.scube.edu.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class DisputeRequest {
	
	private Long id;
	private Long application_id;
	private String phone_no;
	private String email;
	private String reason;
	private String created_by;
	private String contact_person_name;
	private String comment;
	private String status;

}
