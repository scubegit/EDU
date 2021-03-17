package com.scube.edu.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationListPojoResponse {
	
	private String yearr;
	private Long application_id;
	private Long college_id;
	private String doc_status;
	private String document_name;
	private String enrollment_number;
	private String upload_document_path;
	private Long user_id;
	private String ver_request_id;
	

}
