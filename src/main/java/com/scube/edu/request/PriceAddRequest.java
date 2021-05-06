package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString


public class PriceAddRequest {
	
	
	private long id;
	private String serviceName;
	private String yearrangeStart;
	private String yearrangeEnd;
	private long amount;
	private String created_by;
	private long gst;
	private long securitycharge;
	

	

}
