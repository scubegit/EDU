package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class DocumentResponse {
	
	private long id;
    private String documentName;
    private long universityId;

}
