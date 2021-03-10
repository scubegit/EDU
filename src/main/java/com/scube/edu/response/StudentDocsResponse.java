package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString

public class StudentDocsResponse {
	
	private String first_name; 
	private String last_name;
	private Long stream_id;
	private String doc_name;
	private String enroll_no;
	private String year_of_pass_id;
	private Long college_name_id;
	private Long application_id;
	private Long uni_id;
	private Long user_id;
	private String request_type_id;
	private Long ver_req_id;
	private String upload_doc_path;

}
