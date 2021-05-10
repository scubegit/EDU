package com.scube.edu.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")

public class FileStorageProperties {
	
	
	private String uploadDir;
	private String uploadassociateDir;
	
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
	
	

}
