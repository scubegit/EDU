package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class DistrictResponse {
	private Long id;
	private String district;
	private Long StateId;
	private String stateName;
}
