package com.scube.edu.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

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
			
			String newPath = this.fileBaseLocation +"/" + fileSubPath; 
			
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
	
	

}
