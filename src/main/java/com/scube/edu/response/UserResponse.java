package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString

public class UserResponse {

	private long id;
    private String company_name;
    private String email;
    private long role_id;
    private String gstno;
    private String phone_no;
    private String name;
    private String contact_person_phone;
    private String company_email;
    private String contact_person_name;
    private String enroll_no;
    private String first_name;
    private String last_name;
}
