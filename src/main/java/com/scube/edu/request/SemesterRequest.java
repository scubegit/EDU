package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class SemesterRequest {
	
	private long id;
	private long universityid;
	private String semestername;
	private long streamid;

}
