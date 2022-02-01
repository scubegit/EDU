package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class GeneralSummaryResponse {
	
	private String month;
	private String pending;
	private String closed;

}
