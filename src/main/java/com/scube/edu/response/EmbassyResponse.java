package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class EmbassyResponse {
	private long id;
    private String EmbassyName;   
    private long universityId;

}
