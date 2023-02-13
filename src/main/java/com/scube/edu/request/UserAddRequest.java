package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter

public class UserAddRequest {

	private long id;
	private String firstName;
	private String lastName;
	
	private String emailId; //companyEmailID same as  emailId
	private String password;
	private String phoneNumber;
	private long roleId;
	private String gstNo;
	
	private String verificationStatus;
	private String contactPersonName;
	private String companyName; 
	private long universityId; 
	private String contactPersonPhoneNo;
	private String panNumber;
	//private String companyEmailId;
	
	//For Address- Manisha
	
	private String address;
	private String country;
	private String state;
	private String city;
	private String pincode;
	
	//Added by mayuri 
	private String areYouGov;
	private String gstExemption;
	
	

	
}
