package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString

public class StudentMigrationRequest {

//	Basic Details
	private String userid;
	private String fullName;
	private String motherName;
	private String spouceName;
	private String dateOfBirth;
	private String gender; // id??
	private String mobileNumber;
	private String nationality; // id
	private String postalAddr;
	private String postalCountry; // id
	private String postalState; // id
	private String postalDistrict; // id
	private String postalTaluka; // id
	private String postalPincode;
	private String permAddr;
	private String permCountry; // id
	private String permState; // id
	private String permDistrict; // id
	private String permTaluka; // id
	private String permPincode;
//	Migration details
	private String examFaculty;
	private String examName; // id
	private String professionalCourse;
	private String examCode;
	private String seatNumber;
	private String examHeldDate;
	private String prn;
	private String classGrade;
	private String tcNumber;
	private String tcDate;
	private String reason;
	private String leavingYear;
	private String lastCollegeName; // id
	private String paymentId;
	private String streamId; // id
	private String semesterId; // id
	private String branchId; // id
	private String monthOfPassing; // id
	private String yearOfPassingId; // id
	
	private String totalAmt;
	private String totalAmtWithGst;
	
	private String tcFilePath;
	private String docFilePath;
	
}
