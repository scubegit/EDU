package com.scube.edu.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FtpConfiguration {

	
	@Value("${Ftp.Server}")
	private String ftpserver;
	
	@Value("${Ftp.port}")
	private int ftpport;
	
	@Value("${Ftp.user}")
	private String ftpuser;
	
	@Value("${Ftp.pass}")
	private String ftppass;
	
	public void upload(MultipartFile file,  String destination,String fileName) throws IOException {
		FTPClient ftpClient = new FTPClient();
		String server =  ftpserver;
		int port = ftpport;
		String user = ftpuser;
		String pass =ftppass;
		File convFile = null;
		try {
			ftpClient.connect(server, port);
			int reply = ftpClient.getReplyCode();
			System.out.println("FTPReply.isPositiveCompletion(reply) " + FTPReply.isPositiveCompletion(reply));
			ftpClient.enterLocalPassiveMode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				System.err.println("FTP server refused connection.");
				
			}
			ftpClient.login(user, pass);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			ftpClient.makeDirectory(destination);
			  convFile = new File("C:/Users/ADMIN/Desktop/MuTestFolder"+"/"+fileName);   //Need to create temp folder on server this is for local
			  file.transferTo(convFile);
			String fileNameToSave =destination+"/"+fileName;
			
			ftpClient.storeFile(fileNameToSave, new FileInputStream(convFile));		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if(convFile!=null)
			convFile.delete();
		}
	}
	
	
	public byte[] download(String filePath) {

		FTPClient ftpClient = new FTPClient();
		String server =  ftpserver;
		int port = ftpport;
		String user = ftpuser;
		String pass =ftppass;
	
		byte[] as = new byte[0];
		byte bytes[] = new byte[1024];
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			InputStream inputStream = ftpClient.retrieveFileStream(filePath);
			System.out.println("ftp inputstreamss :: " + inputStream);
			
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(bytes)) != -1) {
				ba.write(bytes, 0, bytesRead);
			}
			as = ba.toByteArray();
			inputStream.close();
			return as;

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	
	
	public InputStream readExcel(String filePath) {

		FTPClient ftpClient = new FTPClient();
		String server =  ftpserver;
		int port = ftpport;
		String user = ftpuser;
		String pass =ftppass;
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			InputStream inputStream = ftpClient.retrieveFileStream(filePath);
			
			return inputStream;

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void uploadDataFromScan(InputStream file,  String destination,String fileName) throws IOException {
		FTPClient ftpClient = new FTPClient();
		String server =  ftpserver;
		int port = ftpport;
		String user = ftpuser;
		String pass =ftpuser;
		File convFile = null;
		try {
			ftpClient.connect(server, port);
			int reply = ftpClient.getReplyCode();
			System.out.println("FTPReply.isPositiveCompletion(reply) " + FTPReply.isPositiveCompletion(reply));
			ftpClient.enterLocalPassiveMode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				System.err.println("FTP server refused connection.");
				
			}
			ftpClient.login(user, pass);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			ftpClient.makeDirectory(destination);
			  convFile = new File("C:/Users/ADMIN/Desktop/MuTestFolder"+"/"+fileName);   //Need to create temp folder on server this is for local
			  FileUtils.copyInputStreamToFile(file, convFile);
			String fileNameToSave =destination+"/"+fileName;
			
			ftpClient.storeFile(fileNameToSave, new FileInputStream(convFile));		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if(convFile!=null)
			convFile.delete();
		}
	}

	public void delete(String filePath) {

		FTPClient ftpClient = new FTPClient();
		String server =  ftpserver;
		int port = ftpport;
		String user = ftpuser;
		String pass =ftppass;
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			ftpClient.deleteFile(filePath);
			
			

		} catch (IOException ex) {
			ex.printStackTrace();
			
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}

		finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public boolean isfileExists(String filePath) {

		FTPClient ftpClient = new FTPClient();
		String server =  ftpserver;
		int port = ftpport;
		String user = ftpuser;
		String pass =ftpuser;
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			String[] files = ftpClient.listNames("AutoScanExcel");

		    return Arrays.asList(files).contains(filePath);
			

		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		}

		finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	
}
