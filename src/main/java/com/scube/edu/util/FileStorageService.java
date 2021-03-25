package com.scube.edu.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.exception.FileStorageException;
import com.scube.edu.model.FileStorageProperties;


@Service
public class FileStorageService {
	
	private Path fileStorageLocation;
	
	private final String fileBaseLocation;

	 @Autowired
	
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
	
	

}
