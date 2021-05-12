package com.scube.edu.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class BranchRequest {
	
	private long id;
	private long universityid;
	private String branchname;
	private long streamid;

}
