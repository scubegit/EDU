package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class CountryRequest {
	
	private long id;
	private String countryName;
	private String created_by;
	private String updated_by;
	private String is_deleted;

}
