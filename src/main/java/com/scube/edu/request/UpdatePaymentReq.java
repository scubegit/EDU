package com.scube.edu.request;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class UpdatePaymentReq {

	
	private String[]   verreqidarr ;
	private String paymentid ;
	
    private List<String> verreqid ;
    private String userid;
    
}
