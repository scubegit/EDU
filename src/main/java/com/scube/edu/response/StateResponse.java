package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class StateResponse {
	private Long id;
	private String state;
	private Long CountryId;
	private String countryName;
}
