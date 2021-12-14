package com.scube.edu.service;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class StreamResponse {
	private long id;
    private String StreamName;   
    private long universityId;

}
