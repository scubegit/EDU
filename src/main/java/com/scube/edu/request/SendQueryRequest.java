package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class SendQueryRequest {
	
	private String query;
	private Long userid;

}
