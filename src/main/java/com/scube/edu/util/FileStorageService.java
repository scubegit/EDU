package com.scube.edu.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

	 
	 @Autowired
	 VerificationRequestRepository verificationReqRepository;
	 
	 
	 @Autowired
	 UniversityStudentDocServiceImpl universityStudentDocServiceImpl ;
	 
	 
	  public FileStorageService(FileStorageProperties fileStorageProperties) {
	  
	  this.fileBaseLocation = fileStorageProperties.getUploadDir();
	  
	  }
	 
	
	
	public String storeFile(MultipartFile file, String fileSubPath) {
		
		System.out.println("****fileStorageService storeFile*****"+file);
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//		System.out.println(fileName);
		
		try {
			
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry! File Name contains invalid path sequence!");
			}
			
			String newPath = this.fileBaseLocation +"/" + fileSubPath; 
			
			this.fileStorageLocation = Paths.get(newPath).toAbsolutePath().normalize();
			
			Files.createDirectories(this.fileStorageLocation);
			
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println("fileName" + fileName+ " ---filePath" + targetLocation);
			
			
			return String.valueOf(targetLocation);
		}catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
		
		
		
	}
	
	
	
	
		public Resource loadFileAsResource(String userFor, Long id) throws Exception {
			
		 	String fileName =" ";
		 	try {
	        	
	        	String newPAth = this.fileBaseLocation;
	        	
	        	if(userFor.equalsIgnoreCase("VR")) {
	        		
	        		Optional<VerificationRequest> verifierData = verificationReqRepository.findById(id);
	        		VerificationRequest data = verifierData.get();
	        		
	        		fileName = "file/"+data.getUploadDocumentPath();
	        		
	        		System.out.println("------------fileName--------------"+fileName);
	        		
	        		
	        	}else {
	        		
	        		UniversityStudentDocument doc = universityStudentDocServiceImpl.getUniversityDocDataById(id);
	        		fileName = "file/"+doc.getOriginalDOCuploadfilePath();
	        		
	        		System.out.println("--------InsideElse----fileName--------------"+fileName);
	        		
	        	}
	        
	        	
	          this.fileStorageLocation = Paths.get(newPAth).toAbsolutePath().normalize();
	        	
	            System.out.println(this.fileStorageLocation);
	            
	            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
	            
	            System.out.println(filePath);
	            System.out.println(filePath.toUri());
	            
	            Resource resource = new UrlResource(filePath.toUri());
	            if(resource.exists()) {
	                return resource;
	            } else {
	                throw new Exception("File not found " + fileName);
	            }
	        } catch (Exception ex) {
	            throw new Exception("File not found " + fileName, ex);
	        }
	    }
	
	

}
