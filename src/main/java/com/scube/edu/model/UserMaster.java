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
@Table(	name = "userMaster")
@Getter @Setter
public class UserMaster {
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "userId")
	private Long userId;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "roleId")
	private Long roleId;
	
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "universityId")
	private Long universityId;
	
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "companyName")
	private String companyName;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "Email")
	private String email;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "contactPersonName")
	private String contactPersonName;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "contactPersonPhone")
	private String contactPersonPhone;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "GSTNo")
	private String GSTNo;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "password")
	private String password;
	
	@NotBlank
	@Size(max = 100)
	@Column(name = "emailVerificationStatus")
	private String emailVerificationStatus;
	
	


}
