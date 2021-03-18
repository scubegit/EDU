package com.scube.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name ="verificationDocView")
@Getter @Setter
public class VerificationDocView {
	
	@Id
	@Column(name = "id")
    private Long id;

	@Column(name = "application_id")
    private Long applicationId;
	
	@Column(name = "college_id")
    private Long collegeId;
	
	@Column(name = "document_name")
    private String documentName;
	
	@Column(name = "university_id")
    private Long universityId;
	
	@Column(name = "upload_document_path_name")
    private String uploadDocumentPathName;
	
	@Column(name = "ver_req_id")
    private Long verReqId;
	
	@Column(name = "assigned_to")
    private Long assignedTo;
	
	@Column(name = "doc_amt")
    private Long docAmt;
	
	@Column(name = "doc_amt_with_gst")
    private Long docAmtWithGst;
	
	@Column(name = "year_of_passing")
    private Long yearOfPassing;
	
	@Column(name = "user_id")
    private Long userId;
	
}
