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
public class UserMasterEntity {
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
	
	@Column(name = "userId")
	private String userId;
	

	
	@Column(name = "roleId")
	private Long roleId;
	
	

	
	@Column(name = "universityId")
	private Long universityId;
	
	

	@Size(max = 100)
	@Column(name = "companyName")
	private String companyName;
	

	@Size(max = 100)
	@Column(name = "Email")
	private String email;
	
	
	@Size(max = 100)
	@Column(name = "username")
	private String username;
	
	
	@Size(max = 100)
	@Column(name = "contactPersonPhone")
	private String contactPersonPhone;
	

	@Size(max = 100)
	@Column(name = "GSTNo")
	private String GSTNo;
	

	@Size(max = 100)
	@Column(name = "password")
	private String password;
	
	
	@Size(max = 100)
	@Column(name = "emailVerificationStatus")
	private String emailVerificationStatus;
	
	@Size(max = 100)
	@Column(name = "is_active")
	private String isactive;
	
	public UserMasterEntity() {
		
	}
	
	
    public UserMasterEntity(String email, String GSTNo,String companyname) {
		
		super();
		this.email = email;
		this.GSTNo = GSTNo;
	}
	


}
