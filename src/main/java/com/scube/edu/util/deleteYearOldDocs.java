package com.scube.edu.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.scube.edu.model.FileStorageProperties;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.VerificationRequestRepository;

@Component
public class deleteYearOldDocs {
	
	private Path fileStorageLocation;
	
	private final String fileBaseLocation;
	
	private static final Logger logger = LoggerFactory.getLogger(deleteYearOldDocs.class);
	
	@Autowired
	VerificationRequestRepository verificationReqRepo;
	
	public deleteYearOldDocs(FileStorageProperties fileStorageProperties) {
		
		this.fileBaseLocation = fileStorageProperties.getUploadDir();
		
	}
	
//	@Scheduled(cron = "0 */1 * * * *")
	@Scheduled(cron = "0 0 12 1 * *")
	public void deleteDocs() throws Exception {
	
		String fileSubPath = "file/";
		
		logger.info("Delete year old data");
		
		SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1); // to get previous year add -1
		Date nextYear = cal.getTime();	
		String prevDate = newDate.format(nextYear);

		logger.info(prevDate);
		
		List<VerificationRequest> list = verificationReqRepo.findRecordsByPreviousYearCreatedDate(prevDate);
		
		for(VerificationRequest veriReq: list) {
			
			logger.info("delete document for id = "+ veriReq.getId());
			
			String filename = veriReq.getUploadDocumentPath().split("\\/")[1];
			logger.info(filename);
			
			String newPath = this.fileBaseLocation +"/" + fileSubPath;
			
			this.fileStorageLocation = Paths.get(newPath).toAbsolutePath().normalize();
			
			Path targetLocation = this.fileStorageLocation.resolve(filename);
			logger.info(targetLocation.toString());
			
			Files.delete(targetLocation);
		}
		
	}

}
