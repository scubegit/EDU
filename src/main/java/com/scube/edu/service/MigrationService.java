package com.scube.edu.service;

import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.request.StudentMigrationRequest;

public interface MigrationService {

	boolean saveMigrationRequest(StudentMigrationRequest stuMigReq) throws Exception;

	String saveMigrationDocument(MultipartFile file);

}
