package com.scube.edu.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
//@Transactional
@Table(	name = "verificationRequest")
@Getter @Setter
public class VerificationRequest extends CreateUpdate {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull(message = "Please enter Verification Request ID")
	@Column(name = "verRequestId")
	private Long verRequestId;
	
	
	@NotNull(message = "Please enter applicationID")
	@Column(name = "applicationId")
	private Long applicationId;
	
	@NotNull(message = "Please enter Userid")
	@Column(name = "userId")
	private Long userId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "firstName")
	private String firstName;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "lastName")
	private String lastName;
	
	@NotNull(message = "Please enter Stream")
	@Column(name = "streamId")
	private Long streamId;
	
	@NotNull(message = "Please enter college")
	@Column(name = "collegeId")
	private Long collegeId;
	
	@NotNull(message = "Please enter University")
	@Column(name = "universityId")
	private Long universityId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "document_id")
	private String documentId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "enrollmentNumber")
	private String enrollmentNumber;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "yearOfPassingId")
	private String yearOfPassingId;
	

	@Size(max = 100)
	@Column(name = "uploadDocumentPath")
	private String uploadDocumentPath;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "docStatus")
	private String docStatus;
	
	@NotNull(message = "Please enter Assigned To")
	@Column(name = "assigned_to")
	private Long assignedTo;
	
	@NotNull(message = "Please enter amount without GST")
	@Column(name = "doc_amt")
	private Long docAmt;
	
	@NotNull(message = "Please enter amount with GST")
	@Column(name = "doc_amt_with_gst")
	private Long dosAmtWithGst;
	
	@Column(name = "doc_uni_amt")
	private Long docUniAmt;
	
	@Column(name = "doc_secur_charge")
	private Long docSecurCharge;
	
	@Column(name = "request_type")
	private Long requestType;
	
	@Column(name = "verified_by")
	private Long verifiedBy;
	
	@Column(name = "closed_date")
	private Date closedDate;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "Payment_Flg")
	private String PaymentFlag;
	
	@Column(name = "Payment_ID")
	private String paymentId;

}
