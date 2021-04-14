package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class StreamRequest {
	
	private long id;
	private long universityId;
	private String streamName;
	private String created_by;
	private String updated_by;
	private String is_deleted;

}
