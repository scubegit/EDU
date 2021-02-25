package com.scube.edu.response;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BaseResponse {
	public int respCode;
	private String respMessage;
	private Object respData;
}