package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class StatusChangeRequest {
	
	private long id;
	private String status;
	private long verifiedby;
	private String remark;
	private String roleid;
	private String cgpi;
	//private Long userId;
//	added by kartik
	private String newremark;
	
	private String editreason;

}
