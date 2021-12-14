package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class StateRequest {
	
	private long id;
	private String statename;
	private long countryid;

}
