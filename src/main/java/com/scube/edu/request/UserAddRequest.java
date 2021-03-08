package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter

public class UserAddRequest {

	private long id;
	private String userid; 
	private String contactPersonName;
	private String emailid;
	private String password;
	private String mobilenumber;
	private long roleId;
	private String gstNo;
	
	private String verificationStatus;
	private String companyname; 
	private long universityid; 

	
}
