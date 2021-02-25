package com.scube.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(	name = "payment_Details")
@Getter @Setter
public class PaymentDetails {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "userId")
	private Long userId;
	
	
	@Column(name = "applicationId")
	private Long applicationId;
	
	@Column(name = "paymentAmount")
	private Double paymentAmount;
	
	@Column(name = "GstAmount")
	private Double GstAmount;
	
	@Column(name = "totalPayAmount")
	private Double totalPayAmount;
	
	@Column(name = "payment_ref_no")
	private Long payment_ref_no;
	
	@Column(name = "payment_status")
	private String payment_status;
}
