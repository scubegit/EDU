package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class MisResponse {

	private Long id;
	private String first_name; 
	private String last_name;
	private String stream_name;
	private String semester;
	private String year;
	private String enroll_no;
	private String registerdemailid;
	private String registeredcontactno;
	private String company_name;
	private String paymentdate;

	private Long totalamount;
	private Long univamount;

	private Long securAmt;
	private Long gst;
	private String TransactionId;
	private String actualstatus;
	private String ClosedDate;
	private String monthOfPassing;
	private String doc_status;
	private String bucket;
	
//	added by kartik
	private String remark;
	
	
	
	
	
	
	//private Long stream_id;
	//private String doc_name;
	
	//private String year_of_pass_id;
	//private Long college_name_id;
	//private Long application_id;
	//private Long uni_id;
	//private Long user_id;
	//private String request_type_id;
	//private Long ver_req_id;
	//private String upload_doc_path;
	//private String branch_nm;
	/*
	 * private String semester;
	 * private String req_date;
	 * private Long docAmt; private Long docAmtWithGST;
	 * private Long assigned_to;
	 * private String verifier_name;
	 * private String fullName;
	 * private String college_name;
	 * private String originalDocUploadFilePath;
	 * private String remark;
	 * private String cgpi;
	 */
	
	
}
