package com.scube.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(	name = "yearly_ver_req_backup")
public class YearlyVerReqBackup {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "financial_year")
	private Integer financialYear;
	
	@Column(name = "total_amt")
	private Long totalAmt;
		
	@Column(name = "new_request")
	private String newReq;
	
	@Column(name = "closed_request")
	private String closedReq;
	
	@Column(name = "positive_request")
	private String positiveReq;
	
	@Column(name = "negative_request")
	private String negativeReq;
	
	@Column(name = "dispute_raised")
	private String disputeRaised;
	
	@Column(name = "dispute_clear")
	private String disputeClear;
	
	/*
	 * @Column(name = "company_name") private String companyNm;
	 * 
	 * @Column(name = "gstin_no") private String gstno;
	 * 
	 * @Column(name = "secure_amt") private Long secureAmt;
	 * 
	 * @Column(name = "university_amt") private Long universityAmt;
	 */

}
