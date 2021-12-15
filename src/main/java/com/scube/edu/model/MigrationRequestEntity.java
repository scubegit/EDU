package com.scube.edu.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
//@Transactional
@Table(	name = "migrationRequest")
@Getter @Setter
public class MigrationRequestEntity extends CreateUpdate {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
//	@NotNull(message = "Please enter Verification Request ID")
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "mother_name")
	private String motherName;
	
	@Column(name = "spouce_name")
	private String spouceName;
	
	@Column(name = "date_of_birth")
	private String dateOfBirth;
	
	@Column(name = "gender")
	private String gender;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "nationality")
	private String nationality;
	
	@Column(name = "postal_addr")
	private String postalAddr;
	
	@Column(name = "postal_country")
	private String postalCountry;
	
	@Column(name = "postal_state")
	private String postalState;
	
	@Column(name = "postal_district")
	private String postalDistrict;
	
	@Column(name = "postal_taluka")
	private String postalTaluka;
	
	@Column(name = "postal_pincode")
	private String postalPincode;
	
	@Column(name = "perm_addr")
	private String permAddr;
	
	@Column(name = "perm_country")
	private String permCountry;
	
	@Column(name = "perm_state")
	private String permState;
	
	@Column(name = "perm_district")
	private String permDistrict;
	
	@Column(name = "perm_taluka")
	private String permTaluka;
	
	@Column(name = "perm_pincode")
	private String permPincode;
	
	@Column(name = "exam_faculty")
	private String examFaculty;
	
	@Column(name = "exam_name")
	private String examName;
	
	@Column(name = "professional_course")
	private String professionalCourse;
	
	@Column(name = "exam_code")
	private String examCode;
	
	@Column(name = "seat_number")
	private String seatNumber;
	
	@Column(name = "exam_held_date")
	private String examHeldDate;
	
	@Column(name = "prn")
	private String prn;
	
	@Column(name = "class_grade")
	private String classGrade;
	
	@Column(name = "tc_number")
	private String tcNumber;
	
	@Column(name = "tc_date")
	private String tcDate;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "leaving_year")
	private String leavingYear;
	
	@Column(name = "last_college_name")
	private String lastCollegeName;
	
	@Column(name = "payment_id")
	private String paymentId;
	
	@Column(name = "stream_id")
	private String streamId;
	
	@Column(name = "semester_id")
	private String semesterId;
	
	@Column(name = "branch_id")
	private String branchId;
	
	@Column(name = "yearOfPassingId")
	private String yearOfPassingId;
	
	@Column(name = "monthOfPassing")
	private String monthOfPassing;
	
	@Column(name = "ver_doc_amt")
	private String verDocAmt;
	
	@Column(name = "ver_doc_amt_with_gst")
	private String verDocAmtWithGst;
	
	@Column(name = "mig_amt")
	private String migAmt;
	
	@Column(name = "mig_amt_with_gst")
	private String migAmtWithGst;
	
	@Column(name = "doc_file_path")
	private String docFilePath;
	
	@Column(name = "tcFilePath")
	private String tcFilePath;
	
	@Column(name = "ver_req_id")
	private String verReqId;
	
}
