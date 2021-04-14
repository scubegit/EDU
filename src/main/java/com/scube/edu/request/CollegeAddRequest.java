package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class CollegeAddRequest {

	
	private long id;
	private long universityId;
	private String collegeName;
	private String created_by;
	private String updated_by;
	private String is_deleted;
}
