package com.scube.edu.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.request.StudentMigrationRequest;

public interface MigrationService {

	boolean saveMigrationRequest(StudentMigrationRequest stuMigReq) throws Exception;

	String saveMigrationDocument(MultipartFile file);

	HashMap<String, Long> calculateMigrationAmount(StudentMigrationRequest stuMigReq) throws Exception;

}
