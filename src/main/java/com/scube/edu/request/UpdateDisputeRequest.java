package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class UpdateDisputeRequest {
	
	private long id;
	private String status;
	// status can be 'CandC' OR 'NCandC'

}
