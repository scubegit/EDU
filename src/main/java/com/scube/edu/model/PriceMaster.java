package com.scube.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
	@Column(name = "year_range_start")
	@Size(max = 100)
	private String yearrangeStart;
	
	@NotBlank
	@Column(name = "year_range_end")
	@Size(max = 100)
	private String yearrangeEnd;
	
	
	@NotNull(message = "Please enter Amount")
	@Column(name = "amount")
	private Long amount;
	
	

	@Column(name = "gst")
	private Long gst;
	


	@Column(name = "securCharge")
	private Long securCharge;
	
	
	
	@Column(name = "securGst")
	private Long securGst;
	
	
	
	@Column(name = "totalAmt")
	private Long totalAmt;
	
	
	@Column(name = "totalGst")
	private Long totalGst;
	
	
	
	@Column(name = "discount")
	private Long discount;
	
	
	@Column(name = "rquest_type_id")	
	private Long requestTypeId;
	
	@Column(name = "doc_type_id")	
	private Long doctypeId;
	
}
