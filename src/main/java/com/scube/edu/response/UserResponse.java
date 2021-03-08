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
}
