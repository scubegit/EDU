package com.scube.edu.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")

public class FileStorageProperties {
	
	private String uploadDir;
	private String uploadassociateDir;
	private String imagepathDir;
	private String rejecteddataDir;
	private String migrationTcDocDir;
	public String getImagepathDir() {
		return imagepathDir;
	}

	public void setImagepathDir(String imagepathDir) {
		this.imagepathDir = imagepathDir;
	}

	public String getUploadassociateDir() {
		return uploadassociateDir;
	}

	public void setUploadassociateDir(String uploadassociateDir) {
		this.uploadassociateDir = uploadassociateDir;
	}

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}

	public String getRejecteddataDir() {
		return rejecteddataDir;
	}

	public void setRejecteddataDir(String rejecteddataDir) {
		this.rejecteddataDir = rejecteddataDir;
	}
	
	public String getMigrationTcDocDir() {
		return migrationTcDocDir;
	}

	public void setMigrationTcDocDir(String migrationTcDocDir) {
		this.migrationTcDocDir = migrationTcDocDir;
	}


	
	

}
