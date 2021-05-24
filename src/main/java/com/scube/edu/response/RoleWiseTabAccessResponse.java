package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public class RoleWiseTabAccessResponse {

	private long id;
	private String tabName;
	private Long roleId;

}
