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
@Table(	name = "daily_ver_req_backup")
public class DailyVrBackupEntity {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "req_date")
	private String reqDate;
	
	@Column(name = "total_amt")
	private Long totalAmt;
	
	@Column(name = "company_name")
	private String companyNm;
	
	@Column(name = "secure_amt")
	private Long secureAmt;
	
	@Column(name = "university_amt")
	private Long universityAmt;

}
