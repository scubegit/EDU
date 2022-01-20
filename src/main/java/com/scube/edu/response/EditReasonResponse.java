package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class EditReasonResponse {
	
	private String role; // university OR verifier
	private String reason; // what the reason actually is
	private String date; // on what date the status was changed and request was sent for edit

}
