









































package com.scube.edu.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.controller.VerifierController;
import com.scube.edu.exception.FileStorageException;
import com.scube.edu.model.FileStorageProperties;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.service.UniversityStudentDocServiceImpl;


@Service
public class FileStorageService {
	
	private Path fileStorageLocation;
	
	private final String fileBaseLocation;
	
	private final String fileAssociateBaseLocation;
	
	private final String imagePathDir;

	private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
	 
	 @Autowired
	 VerificationRequestRepository verificationReqRepository;
	 
	 
	 @Autowired
	 UniversityStudentDocServiceImpl universityStudentDocServiceImpl ;
	 
	 
	  public FileStorageService(FileStorageProperties fileStorageProperties) {
	  
	  this.fileBaseLocation = fileStorageProperties.getUploadDir();
	  this.fileAssociateBaseLocation = fileStorageProperties.getUploadassociateDir();
	  this.imagePathDir = fileStorageProperties.getImagepathDir();
	  }
	 
	
	
	public String storeFile(MultipartFile file, String fileSubPath, String flag) {
		
		System.out.println("****fileStorageService storeFile*****"+file);
		
		Date date = new Date(System.currentTimeMillis());
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		String filename = fileName.split("\\.")[0];
		String extension = fileName.split("\\.")[1];
		
		String fileNewName = filename + "_" + date.getTime() + "." + extension;
		
		System.out.println(fileNewName);
		
		try {
			
			if(fileNewName.contains("..")) {
				throw new FileStorageException("Sorry! File Name contains invalid path sequence!");
			}
			String newPath;
			
			if(flag.equalsIgnoreCase("2")) {
				newPath = this.fileAssociateBaseLocation + "/" + fileSubPath;
			}else {
				newPath = this.fileBaseLocation +"/" + fileSubPath; 
			}
			
			this.fileStorageLocation = Paths.get(newPath).toAbsolutePath().normalize();
			
			Files.createDirectories(this.fileStorageLocation);
			
			Path targetLocation = this.fileStorageLocation.resolve(fileNewName);
			
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("fileName" + fileNewName+ " ---filePath" + targetLocation);
			
			String returnPath = fileSubPath + fileNewName;
			
			
			return String.valueOf(returnPath);
		}catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
		
		
		
	}
	
	
	
	
		public Resource loadFileAsResource(String userFor, Long id) throws Exception {
			
		 	String fileName =" ";
		 	logger.info(this.imagePathDir);
		 	try {
	        	
	        	String newPAth ;
	        	
	        	if(userFor.equalsIgnoreCase("VR")) {
	        		
	        		newPAth = this.fileBaseLocation;
	        		Optional<VerificationRequest> verifierData = verificationReqRepository.findById(id);
	        		VerificationRequest data = verifierData.get();
	        		logger.info("doc.vollegename--->"+data.getEnrollmentNumber());
	        		
	        		fileName = data.getUploadDocumentPath();
	        		
	        		System.out.println("------------fileName--------------"+fileName);
	        		logger.info("VR------fileName----->"+ fileName);
	        		
	        	}else {
	        		newPAth = this.fileAssociateBaseLocation;
	        		UniversityStudentDocument doc = universityStudentDocServiceImpl.getUniversityDocDataById(id);
	        		fileName = doc.getOriginalDOCuploadfilePath();
	        		
	        		System.out.println("--------InsideElse----fileName--------------"+fileName);
	        		logger.info("U------fileName----->"+ fileName);
	        	}
	        
	        	
	          this.fileStorageLocation = Paths.get(newPAth).toAbsolutePath().normalize();
	            logger.info("newPath"+ newPAth);
	        	logger.info("fileStorageLocation"+ this.fileStorageLocation);
	            System.out.println(this.fileStorageLocation);
	            
	            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
	            logger.info("filePath"+ filePath);
	            System.out.println(filePath);
	            System.out.println(filePath.toUri());
//	            logger.info(filePath.toUri());
	            
	            
	            Resource resource = new UrlResource(filePath.toUri());
	            if(resource.exists()) {
	            	logger.info("Inside IF(resource.exists)");
	                return resource;
	            } else {
	            	logger.info("Inside else()");
	                throw new Exception("File not found " + fileName);
	            }
	        } catch (Exception ex) {
	            throw new Exception("File not found " + fileName, ex);
	        }
	    }
	
	

}
