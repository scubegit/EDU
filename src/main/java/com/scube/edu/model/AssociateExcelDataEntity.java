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

@Table(	name = "associate_excel_data")
public class AssociateExcelDataEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String Degree;
	private String semester;
	private String seatNo;
	private String monthOfPassing;
	private String passingYear;
	private String imageName;
	
	
}