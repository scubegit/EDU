package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class TalukaResponse {
	private Long id;
	private String taluka;
	private Long DistrictId;
	private String districtName;
}
