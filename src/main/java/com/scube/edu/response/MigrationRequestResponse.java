package com.scube.edu.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class MigrationRequestResponse {
	
	private Long id;
	private String created_date;
	private String fullName;
	private String motherName;
	private String spouceName;
	private String dateOfBirth;
	private String gender;
	private String mobileNumber;
	private String nationality;
	private String postalAddr;
	private String postalCountry;
	private String postalState;
	private String postalDistrict;
	private String postalTaluka;
	private String postalPincode;
	private String permAddr;
	private String permCountry;
	private String permState;
	private String permDistrict;
	private String permTaluka;
	private String permPincode;
	private String examFaculty;
	private String examName;
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
	private String lastCollegeName;
	private String paymentId;
	private String streamId;
	private String semesterId;
	private String branchId;
	private String yearOfPassingId;
	private String monthOfPassing;
	private String verDocAmt;
	private String verDocAmtWithGst;
	private String migAmt;
	private String migAmtWithGst;
	private String migVerTotal;
	private String migVerTotalWithGst;
	private String migSecurCharge;
	private String migUniAmount;
	private String docFilePath;
	private String tcFilePath;
	private String verReqAppId;
	private String migReqStatus;
	private String rejectReason;
	private String verDocStatus;

}
