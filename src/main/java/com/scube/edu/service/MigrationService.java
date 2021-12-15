package com.scube.edu.service;

import com.scube.edu.request.StudentMigrationRequest;

public interface MigrationService {

	boolean saveMigrationRequest(StudentMigrationRequest stuMigReq) throws Exception;

}
