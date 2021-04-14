package com.scube.edu.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString

public class StudentVerificationDocsResponse {
	
	private Long id;
	private String first_name; 
	private String last_name;
	private Long stream_id;
	private String doc_name;
	private String enroll_no;
	private String year;
	private Long college_name_id;
	private Long application_id;
	private Long uni_id;
	private Long user_id;
	private String request_type_id;
	private Long ver_req_id;
	private String upload_doc_path;
	
	private String req_date;

	private Long docAmt;
	private Long docAmtWithGST;

	private Long assigned_to;
	private String doc_status;
	
	private String company_name;
	private String stream_name;
	
	private String originalDocUploadFilePath;
}
