package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class VerificationResponse {
	
	private Long id;
	private String first_name; 
	private String last_name;
	private Long stream_id;
	private String doc_name;
	private String enroll_no;
	private String year;
	private Long year_of_pass_id;
	private Long college_name_id;
	private Long application_id;
	private Long uni_id;
	private Long user_id;
	private Long request_type_id;
	private String request_type;
	private Long ver_req_id;
	private String upload_doc_path;
	private String branch_nm;
	private Long branch_id;
	private String semester;
	private Long semid;
	private String req_date;

	private Long docAmt;
	private Long docAmtWithGST;

	private Long assigned_to;
	private String doc_status;
	
	private String company_name;
	private String stream_name;
	
	private String verifier_name;
	
	private String fullName;
	private String college_name;
	private String originalDocUploadFilePath;
	private String remark;
	private Long amount;
	private Long securAmt;
	private Long gst;
	private String monthOfPassing;
	private String cgpi;
//	added by kartik
	private String editReason;
	private String alt_email;
	private Long doc_id;
	
	//add by rushabh
	private Long embassyid;
	
	//add by mayuri
	private String embassyaddress;
	private String embassyname;


}
