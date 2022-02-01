package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class AgeingSummaryResponse {
	
	private String month;
	private String lessthan;
	private String inbetween;
	private String above;

}
