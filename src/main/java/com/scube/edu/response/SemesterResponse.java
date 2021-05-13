package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class SemesterResponse {
	private Long id;
	private Long universityId;
	private String semester;
	private Long StreamId;
	private String streamName;
}
