package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class RequestTypeResponse {
	
	private long id;
    private String requestType;
    private long universityId;

}
