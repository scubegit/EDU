package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class MigrationStatusChangeRequest {
	
	private String id;
	private String status;
	private String remark;
	private String url;
//	private long verifiedby;
//	private String remark;
//	private String roleid;
//	private String cgpi;
	//private Long userId;
//	added by kartik
//	private String newremark;

}
