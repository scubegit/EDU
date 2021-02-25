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
@Table(	name = "pricemaster")
@Getter @Setter
public class PriceMaster extends CreateUpdate{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Column(name = "year_range")
	@Size(max = 100)
	private String yearrange;
	
	
	@NotBlank
	@Column(name = "amount")
	@Size(max = 100)
	private Long amount;
	
	

	@Column(name = "gst")
	@Size(max = 100)
	private Long gst;
	


	@Column(name = "securCharge")
	@Size(max = 100)
	private Long securCharge;
	
	
	
	@Column(name = "securGst")
	@Size(max = 100)
	private Long securGst;
	
	
	
	@Column(name = "totalAmt")
	@Size(max = 100)
	private Long totalAmt;
	
	
	@Column(name = "totalGst")
	@Size(max = 100)
	private Long totalGst;
	
	
	
	@Column(name = "discount")
	@Size(max = 100)
	private Long discount;
	
}
