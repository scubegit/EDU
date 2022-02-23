package com.scube.edu.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelDataResponse {

	public String id;
	public String degree;
	public String month_of_passing;
	public String passing_year;
	public String seat_no;
	public String semester;
	public String image_name;
	public String status;
	public String massage;
	
	

}
