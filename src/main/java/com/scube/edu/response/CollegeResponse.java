package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class CollegeResponse {
	
	private long id;
    private String collegeName;
    private long universityId;
    private String collegeEmail;

}
