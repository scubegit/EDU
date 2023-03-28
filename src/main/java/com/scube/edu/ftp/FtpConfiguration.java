package com.scube.edu.ftp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scube.edu.util.FileStorageService;

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
	
	@Value("${Ftp.file.localpath}")
	private String ftplocalpath;
	
	private static final Logger logger = LoggerFactory.getLogger(FtpConfiguration.class);

	
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
			logger.info("---------FTPReply.isPositiveCompletion(reply) ----------------" + FTPReply.isPositiveCompletion(reply));

			System.out.println("FTPReply.isPositiveCompletion(reply) " + FTPReply.isPositiveCompletion(reply));
			ftpClient.enterLocalPassiveMode();
			logger.info("---------enterLocalPassiveMode" );

			
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.info("---------11FTP server refused connection." + FTPReply.isPositiveCompletion(reply));
				ftpClient.disconnect();
				System.err.println("FTP server refused connection.");
				logger.info("---------22FTP server refused connection." + FTPReply.isPositiveCompletion(reply));

			}
			ftpClient.login(user, pass);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			logger.info("---------making directory at" + destination);

			
			ftpClient.makeDirectory(destination);
			
			logger.info("---------made directory at" + destination);
			
			logger.info("---------creating new file" + ftplocalpath+"/"+fileName);

			  convFile = new File(ftplocalpath+"/"+fileName);   //Need to create temp folder on server this is for local
				logger.info("---------con file" + ftplocalpath+"/"+fileName);

				 FileUtils.copyToFile(file.getInputStream(),convFile);
				 
			//  file.transferTo(convFile);
			  
				logger.info("---------file transfer to" + convFile);

			String fileNameToSave =destination+"/"+fileName;
			
			logger.info("---------fileNameToSave" + fileNameToSave);

			
			ftpClient.storeFile(fileNameToSave, new FileInputStream(convFile));		
			
			logger.info("---------ftpClient.storeFile" );

			
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
			logger.info("--------ex download----------------" + ex.toString());
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

	
	
	
	public byte[] download1(String filePath) {

		FTPClient ftpClient = new FTPClient();
		String server =  ftpserver;
		int port = ftpport;
		String user = ftpuser;
		String pass =ftppass;
		
		 filePath="/AutoScanExcel/IMG_2022_11_11_12_47_44.jpg";
	
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
			
			 File fileObj = new File("D:/myDoc/test.jpg");
				
				OutputStream outputStream            = new BufferedOutputStream(new FileOutputStream(fileObj));
	                
			
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, bytesRead);
				
				ba.write(bytes, 0, bytesRead);
			}
			as = ba.toByteArray();
			
			
                
			inputStream.close();
			return as;

		} catch (IOException ex) {
			logger.info("--------ex download----------------" + ex.toString());
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
	
	
//public byte[] readExcel(String filePath) {
		public InputStream readExcel(String filePath) {
		
		FTPClient ftpClient = new FTPClient();
		String server =  ftpserver;
		int port = ftpport;
		String user = ftpuser;
		String pass =ftppass;
		byte bytes[] = new byte[1024];
		byte[] as = new byte[0];

		
		try {
			ftpClient.connect(server, port);
			int reply = ftpClient.getReplyCode();
			logger.info("---------FTPReply.isPositiveCompletion(reply)11 ----------------" + FTPReply.isPositiveCompletion(reply));

			ftpClient.login(user, pass);
			
			ftpClient.enterLocalPassiveMode();
			
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			InputStream inputStream = ftpClient.retrieveFileStream(filePath);
			logger.info("--------ex readexcel----------------" +inputStream);

			
			/*
			 * ByteArrayOutputStream ba = new ByteArrayOutputStream(); int bytesRead = -1;
			 * while ((bytesRead = inputStream.read(bytes)) != -1) { ba.write(bytes, 0,
			 * bytesRead); } as = ba.toByteArray(); inputStream.close(); return as;
			 */
			
			return inputStream;

		} catch (IOException ex) {
			logger.info("--------ex readexcel----------------" + ex.toString());
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


public byte[] readExcel1(String filePath) {
	//	public InputStream readExcel(String filePath) {
		
		FTPClient ftpClient = new FTPClient();
		String server =  ftpserver;
		int port = ftpport;
		String user = ftpuser;
		String pass =ftppass;
		byte bytes[] = new byte[1024];
		byte[] as = new byte[0];

		
		try {
			ftpClient.connect(server, port);
			int reply = ftpClient.getReplyCode();
			logger.info("----readExcel1--1---FTPReply.isPositiveCompletion(reply)11 ----------------" + FTPReply.isPositiveCompletion(reply));

			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			InputStream inputStream = ftpClient.retrieveFileStream(filePath);
			
			logger.info("---------readExcel1---2----inputStream"+inputStream);

			
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(bytes)) != -1) {
				ba.write(bytes, 0, bytesRead);
			}
			as = ba.toByteArray();
			inputStream.close();
			logger.info("---------readExcel1---3--as"+as.toString());
			
			return as;
			
			
			//return inputStream;

		} catch (IOException ex) {
			logger.info("--------ex readexcel----------------" + ex.toString());
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

private static void showServerReply(FTPClient ftpClient) {
    String[] replies = ftpClient.getReplyStrings();
    if (replies != null && replies.length > 0) {
        for (String aReply : replies) {
            System.out.println("SERVER: " + aReply);
        }
    }
}

	public void uploadDataFromScan(InputStream file,  String destination,String fileName) throws IOException {
		FTPClient ftpClient = new FTPClient();
		String server =  ftpserver;
		int port = ftpport;
		String user = ftpuser;
		String pass =ftppass;
		File convFile = null;
		try {
			ftpClient.connect(server, port);
			int reply = ftpClient.getReplyCode();
			logger.info("FTPReply.isPositiveCompletion(reply) " + FTPReply.isPositiveCompletion(reply));
			ftpClient.enterLocalPassiveMode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.info("FTP server refused connection.");
				
			}
			logger.info("user--"+user+"--pass--"+pass);
			
			boolean loginresponse=ftpClient.login(user, pass);
			
			logger.info("loginresponse--"+loginresponse);
			
			showServerReply(ftpClient);

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			logger.info("destination--"+destination);
			
			boolean dirflag=ftpClient.makeDirectory(destination);
			
			showServerReply(ftpClient);
			
			logger.info("uploadDataFromScan dirflag--"+dirflag +"--");
			boolean dirflag1=ftpClient.changeWorkingDirectory(destination);
			
			showServerReply(ftpClient);

			
			logger.info("uploadDataFromScan dirflag1--"+dirflag1);
			//  convFile = new File("C:/Users/ADMIN/Desktop/MuTestFolder"+"/"+fileName);   //Need to create temp folder on server this is for local
			
			convFile = new File(ftplocalpath+"/"+fileName); 
			
			logger.info(" uploadDataFromScan convFile "+convFile);
			
			  FileUtils.copyInputStreamToFile(file, convFile);
			String fileNameToSave =destination+"/"+fileName;
			
			logger.info(" uploadDataFromScan fileNameToSave "+fileNameToSave);

			
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
			logger.info("on connect"); 
			showServerReply(ftpClient);

			
			ftpClient.login(user, pass);
			logger.info("on login"); 

			showServerReply(ftpClient);

			
			ftpClient.enterLocalPassiveMode();
			logger.info("on mode"); 

			showServerReply(ftpClient);

			
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			
			ftpClient.setBufferSize(0);
			
			
			ftpClient.deleteFile(filePath);
			logger.info("on delete"); 

			showServerReply(ftpClient);

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
		String pass =ftppass;
		try {
			ftpClient.connect(server, port);
			
			int reply = ftpClient.getReplyCode();
			logger.info("---------isfileExists FTPReply.isPositiveCompletion(reply)22 ----------------" + FTPReply.isPositiveCompletion(reply));
			
			if (!FTPReply.isPositiveCompletion(reply)) {
				logger.info("---------11FTP server refused connection." + FTPReply.isPositiveCompletion(reply));
				ftpClient.disconnect();
				System.err.println("FTP server refused connection.");
				logger.info("---------22FTP server refused connection." + FTPReply.isPositiveCompletion(reply));

			}
			
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setBufferSize(0);
			String[] files = ftpClient.listNames("/AutoScanExcel");

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
