package com.scube.edu.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
public  class TopFiverYearRevenueResponse {

	private String year;
	private double totalAmt;
}
