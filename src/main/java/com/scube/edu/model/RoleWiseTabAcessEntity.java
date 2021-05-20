package com.scube.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(	name = "role_wise_tab_access")
@Getter @Setter
public class RoleWiseTabAcessEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Size(max = 100)
	@Column(name = "tab_name")
	private String tabName;
	
	@Column(name = "RoleId")
	private Long roleId;
	
}
