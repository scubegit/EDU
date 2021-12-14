package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class DistrictRequest {
	
	private long id;
	private String districtname;
	private long stateid;

}
