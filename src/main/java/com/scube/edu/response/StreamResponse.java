package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class StreamResponse {
	private long id;
    private String streamName;
   
    private long universityId;

}
