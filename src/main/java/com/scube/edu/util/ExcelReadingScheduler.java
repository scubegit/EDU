package com.scube.edu.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import javax.mail.Multipart;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scube.edu.awsconfig.FileStore;
import com.scube.edu.ftp.FtpConfiguration;
import com.scube.edu.model.AssociateExcelDataEntity;
import com.scube.edu.model.UniversityStudentDocument;
import com.scube.edu.model.checkautoReadingActiveEntity;
import com.scube.edu.repository.AssociateExcelDataRepository;
import com.scube.edu.repository.ChkAutoReadingStatus;
import com.scube.edu.repository.UniversityStudentDocRepository;
import com.scube.edu.response.ExcelDataResponse;
import com.scube.edu.response.UniversityStudDocResponse;
import com.scube.edu.service.AssociateManagerService;
import com.scube.edu.service.AssociateManagerServiceImpl;
import com.scube.edu.service.EmailService;
import com.scube.edu.response.UniversityStudDocResponse;

@Service
public class ExcelReadingScheduler {

	private static final Logger logger = LoggerFactory.getLogger(AssociateManagerServiceImpl.class);

	@Autowired
	private AssociateManagerService associateManagerService;

	@Autowired
	FileStore fileStore;
	
	@Autowired
	FtpConfiguration ftpConfiguration;
	
	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private EmailService emailService;
	

	@Autowired
	AssociateExcelDataRepository associateExcelDataRepository; 
	
	@Autowired
	ChkAutoReadingStatus chkAutoReadingStatus;
	
	@Autowired
	 UniversityStudentDocRepository 	 universityStudentDocRepository ;

	@Value("${file.awsORtest}")
	private String awsORtest;

	 @Value("${csv.file.folder}")
	   private String csvFileLocation;
	 
	/*
	 * @Value("${file.RejectedData-Files}") private String rejcsvfile;
	 */
	 
	/*
	 * @Value("${csv.filename}") private String csvfilenm;
	 */
	 
	 @Value("${img.folder}")
	    private String imgLocation;
	
	//@Scheduled(cron = "${cron.time.excelRead}")
	public String readExcelFiles() throws Exception, IOException {		
		String result = "";
		try {
		if(awsORtest.equalsIgnoreCase("AWS")) {

			Optional<checkautoReadingActiveEntity> chk= chkAutoReadingStatus.findById((long) 1);
			checkautoReadingActiveEntity ischk=chk.get();
			String chkflag = null;
			if(ischk!=null)
			{
				if(ischk.getFlag()!=null){
					chkflag=ischk.getFlag();
				}
			}
			if(chkflag!=null) {
			if(chkflag.equalsIgnoreCase("InActive")) {
				String statusflag="Active";
				chkAutoReadingStatus.updateFlg(statusflag);

		logger.info("********Enterning ExcelReadingScheduler readExcelFiles********");
		List<UniversityStudDocResponse> studentDataReviewList = new ArrayList<UniversityStudDocResponse>();
		String imagefile = null;
		String imagefilenm=null;
		String previmagefile = "";
		
		File emailexcelstorePath = null;
		int k;
		int check = 0;
		String filePath = null;
		String fileSubPath = "file/";
		String line;
		String chekrow;

		String csvnm;
         
		String folder=csvFileLocation;
		//String flnm=csvfilenm;
		S3Object fi = fileStore.readExcel(csvFileLocation);
		if(fi!=null) {
		InputStream csvstream = fi.getObjectContent();

		if(csvstream!=null) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(csvstream));

		System.out.println("--File content:");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (check == 0) {
				check++;
				continue;
			}

			String[] datalist = line.split(",");
			logger.info("" + datalist);
			UniversityStudDocResponse studentData = new UniversityStudDocResponse();

			for (k = 0; k < datalist.length; k++) {

				if (k == 0) {
					studentData.setStream(datalist[0]);
				} else if (k == 1) {
					studentData.setSemester(datalist[1]);

				} else if (k == 2) {
					studentData.setEnrollmentNo(datalist[2]);

				} else if (k == 3) {
					studentData.setMonthOfPassing(datalist[3]);
				}
				else if (k == 4) {
					studentData.setPassingYear(datalist[4]);

				} else if (k == 5) {
					imagefilenm = datalist[5];
					imagefile=imgLocation;
					logger.info("imageFileNAme" + imagefile);

					String flag = "2";

					Date date1 = new Date();
					SimpleDateFormat formatter1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
					String strdate1 = formatter1.format(date1);
					strdate1 = strdate1.replace(" ", "_");
					strdate1 = strdate1.replace(":", "-");
					if (!previmagefile.equals(imagefilenm)) {
						S3Object imgdata = fileStore.getimage(imagefile,imagefilenm);
						if (imgdata != null) {

							InputStream imagestream = imgdata.getObjectContent();

							filePath = fileStorageService.schedulerstoreFileOnAws(imagestream, flag, imagefilenm);

							S3Object mvimgdata = fileStore.getimage(imagefile,imagefilenm);
							InputStream movedimagestream = mvimgdata.getObjectContent();

							String filename = imagefilenm.split("\\.")[0];
							String extension = imagefilenm.split("\\.")[1];

							String newimagefilenm = filename+"_" + strdate1 +  "." + extension;
							fileStorageService.MoveCsvAndImgToArchive(movedimagestream, newimagefilenm, "1");
							fileStore.deleteFile(mvimgdata.getKey());

						} else {
							filePath = "ImageNotAvailable";
						}
					}
					previmagefile = imagefilenm;
					studentData.setOriginalDOCuploadfilePath(filePath);

				}

			}
			studentDataReviewList.add(studentData);

		}
		
		}
		else
		{
			result="File is Empty/Blank";
		}
		
		Date date1 = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strdate1 = formatter1.format(date1);
		strdate1 = strdate1.replace(" ", "_");
		strdate1 = strdate1.replace(":", "-");

		csvnm = strdate1 + ".csv";
		S3Object moveddat = fileStore.readExcel(csvFileLocation);
		if(moveddat!=null) {
		InputStream mvcsvstream = moveddat.getObjectContent();
		if(mvcsvstream!=null){
		fileStorageService.MoveCsvAndImgToArchive(mvcsvstream, csvnm, "1");
		

		fileStore.deleteFile(fi.getKey());
		}
		}

		// reader.close();
		long id = 0000;
		
		HashMap<String, List<UniversityStudDocResponse>> resp = associateManagerService
				.AutosaveStudentInfo(studentDataReviewList, id);

		if (resp.get("RejectedData") != null && !resp.get("RejectedData").isEmpty()) {
			List<UniversityStudDocResponse> response = resp.get("RejectedData");

			XSSFWorkbook workbook = new XSSFWorkbook();

			// spreadsheet object
			XSSFSheet sheet = workbook.createSheet(" Student Data ");

			int rownum = 0;
			// XSSFRow row = null;
			Row row = sheet.createRow(0);

			Cell cell = row.createCell(0);
			cell.setCellValue("Degree");

			cell = row.createCell(1);
			cell.setCellValue("Semester");

			cell = row.createCell(2);
			cell.setCellValue("Seat No.");
			
			cell = row.createCell(3);
			cell.setCellValue("MonthOfPassing");

			cell = row.createCell(4);
			cell.setCellValue("YearOfPassing");

			cell = row.createCell(5);
			cell.setCellValue("Reasons");
			rownum++;
			for (UniversityStudDocResponse user : response) {
				row = sheet.createRow(rownum++);
				createList(user, row);
			}

			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String strdate = formatter.format(date);
			strdate = strdate.replace(" ", "_");
			strdate = strdate.replace(":", "-");

			if(awsORtest.equalsIgnoreCase("AWS")) {
				String returnpath=fileStorageService.storeRejectedDataFile("Rejected_Data_" + strdate + ".csv");
			    emailexcelstorePath = new File(returnpath);
			}
			else {
				
				emailexcelstorePath = new File("./Rejected_Csv/Rejected_Data_" + strdate + ".csv");

			}
			FileOutputStream out = new FileOutputStream(emailexcelstorePath);
			workbook.write(out);
			out.close();
			InputStream targetStream = new FileInputStream(emailexcelstorePath);

			fileStorageService.MoveCsvAndImgToArchive(targetStream, emailexcelstorePath.getName(), "2");

			emailService.sendRejectedDatamail(emailexcelstorePath);
		
			if(emailexcelstorePath.delete())
	        {
	            System.out.println("File deleted successfully From EDU");
	        }
	        else
	        {
	            System.out.println("Failed to delete the file From EDU");
	        }
			result="Added data successfully, Rejected Data sent via mail";

		}
		else {
			result="Added data succefully,No data is Rejected from this file";
		}

		}
		else
		{
			result="File not found to read data";
			logger.info("********result********"+result);

		}
		logger.info("********result********"+result);

	
		String statusflag1="InActive";
		chkAutoReadingStatus.updateFlg(statusflag1);
			}
			else
			{
				result="Process already active!";
				logger.info("********result********"+result);

			}
			
		}
		}
		if(awsORtest.equalsIgnoreCase("InHouse")) {


			Optional<checkautoReadingActiveEntity> chk= chkAutoReadingStatus.findById((long) 1);
			checkautoReadingActiveEntity ischk=chk.get();
			String chkflag = null;
			if(ischk!=null)
			{
				if(ischk.getFlag()!=null){
					chkflag=ischk.getFlag();
				}
			}
			if(chkflag!=null) {
			if(chkflag.equalsIgnoreCase("InActive")) {
				String statusflag="Active";
				chkAutoReadingStatus.updateFlg(statusflag);

		logger.info("********Enterning ExcelReadingScheduler readExcelFiles********");
		List<UniversityStudDocResponse> studentDataReviewList = new ArrayList<UniversityStudDocResponse>();
		String imagefile = null;
		String imagefilenm=null;
		String previmagefile = "";
		
		File emailexcelstorePath = null;
		int k;
		int check = 0;
		String filePath = null;
		String fileSubPath = "file/";
		String line;
		String chekrow;

		String csvnm;
         
		String folder=csvFileLocation;
		//String flnm=csvfilenm;
		S3Object fi =null;// ftpConfiguration.readExcel(csvFileLocation);
		
		
	
		InputStream csvstream = ftpConfiguration.readExcel(csvFileLocation);

		if(csvstream!=null) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(csvstream));

		System.out.println("--File content:");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (check == 0) {
				check++;
				continue;
			}

			String[] datalist = line.split(",");
			logger.info("" + datalist);
			UniversityStudDocResponse studentData = new UniversityStudDocResponse();

			for (k = 0; k < datalist.length; k++) {

				if (k == 0) {
					studentData.setStream(datalist[0]);
				} else if (k == 1) {
					studentData.setSemester(datalist[1]);

				} else if (k == 2) {
					studentData.setEnrollmentNo(datalist[2]);

				} else if (k == 3) {
					studentData.setMonthOfPassing(datalist[3]);
				}
				else if (k == 4) {
					studentData.setPassingYear(datalist[4]);

				} else if (k == 5) {
					imagefilenm = datalist[5];
					imagefile=imgLocation;
					logger.info("imageFileNAme" + imagefile);

					String flag = "2";

					Date date1 = new Date();
					SimpleDateFormat formatter1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
					String strdate1 = formatter1.format(date1);
					strdate1 = strdate1.replace(" ", "_");
					strdate1 = strdate1.replace(":", "-");
					if (!previmagefile.equals(imagefilenm)) {
						S3Object imgdata = fileStore.getimage(imagefile,imagefilenm);
						if (imgdata != null) {

							InputStream imagestream = ftpConfiguration.readExcel(imagefile+imagefilenm);

							filePath = fileStorageService.storeScanFileOnFtp(imagestream, flag, imagefilenm);

							String filename = imagefilenm.split("\\.")[0];
							String extension = imagefilenm.split("\\.")[1];

							String newimagefilenm = filename+"_" + strdate1 +  "." + extension;
							fileStorageService.storeScanFileOnFtpToArchive(imagestream, newimagefilenm, "1");
							ftpConfiguration.delete(imagefile+imagefilenm);

						} else {
							filePath = "ImageNotAvailable";
						}
					}
					previmagefile = imagefilenm;
					studentData.setOriginalDOCuploadfilePath(filePath);

				}

			}
			studentDataReviewList.add(studentData);

		}
		
		}
		else
		{
			result="File is Empty/Blank";
		}
		
		Date date1 = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strdate1 = formatter1.format(date1);
		strdate1 = strdate1.replace(" ", "_");
		strdate1 = strdate1.replace(":", "-");

		csvnm = strdate1 + ".csv";
		
		if(csvstream!=null){
		fileStorageService.storeScanFileOnFtpToArchive(csvstream, csvnm, "1");
		ftpConfiguration.delete(imagefile+imagefilenm);
		}

		// reader.close();
		long id = 0000;
		
		HashMap<String, List<UniversityStudDocResponse>> resp = associateManagerService
				.AutosaveStudentInfo(studentDataReviewList, id);

		if (resp.get("RejectedData") != null && !resp.get("RejectedData").isEmpty()) {
			List<UniversityStudDocResponse> response = resp.get("RejectedData");

			XSSFWorkbook workbook = new XSSFWorkbook();

			// spreadsheet object
			XSSFSheet sheet = workbook.createSheet(" Student Data ");

			int rownum = 0;
			// XSSFRow row = null;
			Row row = sheet.createRow(0);

			Cell cell = row.createCell(0);
			cell.setCellValue("Degree");

			cell = row.createCell(1);
			cell.setCellValue("Semester");

			cell = row.createCell(2);
			cell.setCellValue("Seat No.");
			
			cell = row.createCell(3);
			cell.setCellValue("MonthOfPassing");

			cell = row.createCell(4);
			cell.setCellValue("YearOfPassing");

			cell = row.createCell(5);
			cell.setCellValue("Reasons");
			rownum++;
			for (UniversityStudDocResponse user : response) {
				row = sheet.createRow(rownum++);
				createList(user, row);
			}

			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String strdate = formatter.format(date);
			strdate = strdate.replace(" ", "_");
			strdate = strdate.replace(":", "-");

			if(awsORtest.equalsIgnoreCase("InHouse")) {
				String returnpath=fileStorageService.storeRejectedDataFile("Rejected_Data_" + strdate + ".csv");
			    emailexcelstorePath = new File(returnpath);
			}
			else {
				
				emailexcelstorePath = new File("./Rejected_Csv/Rejected_Data_" + strdate + ".csv");

			}
			FileOutputStream out = new FileOutputStream(emailexcelstorePath);
			workbook.write(out);
			out.close();
			InputStream targetStream = new FileInputStream(emailexcelstorePath);

			fileStorageService.MoveCsvAndImgToArchive(targetStream, emailexcelstorePath.getName(), "2");

			emailService.sendRejectedDatamail(emailexcelstorePath);
		
			if(emailexcelstorePath.delete())
	        {
	            System.out.println("File deleted successfully From EDU");
	        }
	        else
	        {
	            System.out.println("Failed to delete the file From EDU");
	        }
			result="Added data successfully, Rejected Data sent via mail";

		}
		else {
			result="Added data succefully,No data is Rejected from this file";
		}

		}
		else
		{
			result="File not found to read data";
			logger.info("********result********"+result);

		}
		logger.info("********result********"+result);

	
		String statusflag1="InActive";
		chkAutoReadingStatus.updateFlg(statusflag1);
			}
			else
			{
				result="Process already active!";
				logger.info("********result********"+result);

			}
			
		}
		
		
		}
		catch(Exception e) {
			String statusflag1="InActive";
			result="Unable to read file,check file format/Data and try again!";
			chkAutoReadingStatus.updateFlg(statusflag1);
		}
		logger.info("******** Exiting ExcelReadingScheduler readExcelFiles********");


		return result;

	}
	
	
	
	public String readExcelFilesAndSaveToTable() throws Exception, IOException {		
		String result = "";
		try {
		if(awsORtest.equalsIgnoreCase("AWS")) {

			Optional<checkautoReadingActiveEntity> chk= chkAutoReadingStatus.findById((long) 1);
			checkautoReadingActiveEntity ischk=chk.get();
			String chkflag = null;
			if(ischk!=null)
			{
				if(ischk.getFlag()!=null){
					chkflag=ischk.getFlag();
				}
			}
			if(chkflag!=null) {
			if(chkflag.equalsIgnoreCase("InActive")) {
				String statusflag="Active";
				chkAutoReadingStatus.updateFlg(statusflag);

		logger.info("********Enterning ExcelReadingScheduler readExcelFiles*****start***",new Date().getTime());
		List<AssociateExcelDataEntity> studentDataReviewList = new ArrayList<AssociateExcelDataEntity>();
		String imagefile = null;
		String imagefilenm=null;
		String previmagefile = "";
		
		File emailexcelstorePath = null;
		int k;
		int check = 0;
		String filePath = "ImageNotAvailable";
		String filePathStatus = "Not";
		String fileSubPath = "file/";
		String line;
		String chekrow;

		String csvnm;
         
		String folder=csvFileLocation;
		//String flnm=csvfilenm;
// keshav test 		
		logger.info("*******readExcel***start***",new Date().getTime());
		S3Object fi = fileStore.readExcel(csvFileLocation);
		logger.info("*******readExcel*AWS return **start***",new Date().getTime());
//		S3Object fi = fileStore.readExcel("MovedAutoScanCsvAndImg/01-12-2021_06-28-24_new.csv");
//		S3Object fi = fileStore.readExcel("MovedAutoScanCsvAndImg/upload.csv");
		
		if(fi!=null) {
		InputStream csvstream = fi.getObjectContent();

		if(csvstream!=null) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(csvstream));

//		System.out.println("--File content:");
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (check == 0) {
				check++;
				continue;
			}

			String[] datalist = line.split(",");
			logger.info("" + datalist);
			AssociateExcelDataEntity studentData = new AssociateExcelDataEntity();

			for (k = 0; k < datalist.length; k++) {

				if (k == 0) {
//					studentData.setStream(datalist[0]);
					studentData.setDegree(datalist[0]);
					
				} else if (k == 1) {
					studentData.setSemester(datalist[1]);

				} else if (k == 2) {
//					studentData.setEnrollmentNo(datalist[2]);
					studentData.setSeatNo(datalist[2]);

				} else if (k == 3) {
					studentData.setMonthOfPassing(datalist[3]);
				}
				else if (k == 4) {
//					studentData.setPassingYear(datalist[4]);
					studentData.setPassingYear(datalist[4]);

				} else if (k == 5) {
					imagefilenm = datalist[5];
					
					previmagefile = imagefilenm;
					studentData.setImageName(imagefilenm);
					
					/*
					 * logger.info("imageFileNAme" + imagefile);
					 * 
					 * imagefile=imgLocation; logger.info("imageFileNAme" + imagefile);
					 * 
					 * String flag = "2";
					 * 
					 * Date date1 = new Date(); SimpleDateFormat formatter1 = new
					 * SimpleDateFormat("dd-M-yyyy hh:mm:ss"); String strdate1 =
					 * formatter1.format(date1); strdate1 = strdate1.replace(" ", "_"); strdate1 =
					 * strdate1.replace(":", "-"); if (!previmagefile.equals(imagefilenm)) {
					 * S3Object imgdata = fileStore.getimage(imagefile,imagefilenm); //S3Object
					 * imgdata = fileStore.getimage(
					 * "file/associate_docs/00001882-104f-4e20-800b-0c0f4f66a9ff/",imagefilenm); if
					 * (imgdata != null) {
					 * 
					 * 
					 * InputStream imagestream = imgdata.getObjectContent();
					 * 
					 * filePath = fileStorageService.schedulerstoreFileOnAws(imagestream, flag,
					 * imagefilenm);
					 * 
					 * S3Object mvimgdata = fileStore.getimage(imagefile,imagefilenm); InputStream
					 * movedimagestream = mvimgdata.getObjectContent();
					 * 
					 * String filename = imagefilenm.split("\\.")[0]; String extension =
					 * imagefilenm.split("\\.")[1];
					 * 
					 * String newimagefilenm = filename+"_" + strdate1 + "." + extension;
					 * fileStorageService.MoveCsvAndImgToArchive(movedimagestream, newimagefilenm,
					 * "1"); fileStore.deleteFile(mvimgdata.getKey());
					 * 
					 * } else { filePath = "ImageNotAvailable"; } } previmagefile = imagefilenm;
					 * studentData.setImageName(imagefilenm);
					 */

				}

			}
			studentDataReviewList.add(studentData);

		}
		
		logger.info("*******excel data save to table***",new Date().getTime());
		
		associateExcelDataRepository.daleteExcelData();
		
		associateExcelDataRepository.saveAll(studentDataReviewList);
		
		
		
		XSSFWorkbook workbook = new XSSFWorkbook();

		// spreadsheet object
		XSSFSheet sheet = workbook.createSheet(" Student Data ");

		int rownum = 0;
		// XSSFRow row = null;
		Row row = sheet.createRow(0);

		Cell cell = row.createCell(0);
		cell.setCellValue("Degree");

		cell = row.createCell(1);
		cell.setCellValue("Semester");

		cell = row.createCell(2);
		cell.setCellValue("Seat No.");
		
		cell = row.createCell(3);
		cell.setCellValue("MonthOfPassing");

		cell = row.createCell(4);
		cell.setCellValue("YearOfPassing");

		cell = row.createCell(5);
		cell.setCellValue("Reasons");
		rownum++;
		
		
		logger.info("*******send data to PROCEDURE***",new Date().getTime());
		
		List<Map<String ,Object>> excelCompareList = associateExcelDataRepository.excelCompare();
		
		logger.info("******* PROCEDURE return data ***",new Date().getTime());
		
		final ObjectMapper mapper = new ObjectMapper();
		
		List<ExcelDataResponse> rejectList = new ArrayList<>();
		List<ExcelDataResponse> selectedList = new ArrayList<>();
		
		List<UniversityStudentDocument> studentDataList = new ArrayList<UniversityStudentDocument>();
		
		
		logger.info("*******img read start ***",new Date().getTime());
		for(int i=0; i<excelCompareList.size(); i++) {
			final ExcelDataResponse excelDataResponse = mapper.convertValue(excelCompareList.get(i), ExcelDataResponse.class);
	//		logger.info(String.valueOf(i) + "-->"+excelDataResponse);
			
			
			if(excelDataResponse.getStatus().equalsIgnoreCase("selected")){

			imagefile=imgLocation;
	//		logger.info("imageFileNAme" + imagefile);

			String flag = "2";
			
			imagefilenm = excelDataResponse.getImage_name();

			Date date1 = new Date();
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String strdate1 = formatter1.format(date1);
			strdate1 = strdate1.replace(" ", "_");
			strdate1 = strdate1.replace(":", "-");
				if (!previmagefile.equals(imagefilenm)) {
					S3Object imgdata = fileStore.getimage(imagefile,imagefilenm);
					if (imgdata != null) {
	
						InputStream imagestream = imgdata.getObjectContent();
	
						filePath = fileStorageService.schedulerstoreFileOnAws(imagestream, flag, imagefilenm);
	
						S3Object mvimgdata = fileStore.getimage(imagefile,imagefilenm);
						InputStream movedimagestream = mvimgdata.getObjectContent();
	
						String filename = imagefilenm.split("\\.")[0];
						String extension = imagefilenm.split("\\.")[1];
	
						String newimagefilenm = filename+"_" + strdate1 +  "." + extension;
						fileStorageService.MoveCsvAndImgToArchive(movedimagestream, newimagefilenm, "1");
					//	fileStore.deleteFile(mvimgdata.getKey());
						filePathStatus = "";
					} else {
						filePath = "ImageNotAvailable";
						filePathStatus = "Not";
					}
				}
					previmagefile = imagefilenm;
					excelDataResponse.setImage_name(filePath);
					excelDataResponse.setStatus(filePathStatus.equalsIgnoreCase("Not") ? "Reject" : "selected" );
					excelDataResponse.setMassage("Image"+filePathStatus+"Available");
				
			}
			
		//	System.out.println("excelDataResponse.================"+excelDataResponse.getStatus());
			
			
			if(excelDataResponse.getStatus().equalsIgnoreCase("Reject")){
				
				row = sheet.createRow(rownum++);
				createListNew(excelDataResponse, row);
				
			}else {
			
				long id = 0000;
				
				UniversityStudentDocument studentData=new UniversityStudentDocument();

		        studentData.setStreamId(Long.parseLong(excelDataResponse.getDegree()));	
		        studentData.setEnrollmentNo(excelDataResponse.getSeat_no());
		        studentData.setPassingYearId(Long.parseLong(excelDataResponse.getPassing_year()));	
		        studentData.setOriginalDOCuploadfilePath(excelDataResponse.getImage_name());
		        studentData.setSemId(Long.parseLong(excelDataResponse.getSemester()));
		       // studentData.setBranchId(branch.getId());
		        studentData.setCreatedate(new Date());
		        studentData.setCreateby("000");
		        studentData.setMonthOfPassing(excelDataResponse.getMonth_of_passing());
				
				
				studentDataList.add(studentData);
			}
				
		}
		
		logger.info("*******img read End ***",new Date().getTime());
		
		// save 
		universityStudentDocRepository.saveAll(studentDataList);
//		
				
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
				String strdate = formatter.format(date);
				strdate = strdate.replace(" ", "_");
				strdate = strdate.replace(":", "-");
		
				if(awsORtest.equalsIgnoreCase("AWS")) {
					String returnpath=fileStorageService.storeRejectedDataFile("Rejected_Data_" + strdate + ".csv");
				    emailexcelstorePath = new File(returnpath);
				}
				else {
					
					emailexcelstorePath = new File("./Rejected_Csv/Rejected_Data_" + strdate + ".csv");
		
				}
				FileOutputStream out = new FileOutputStream(emailexcelstorePath);
				workbook.write(out);
				out.close();
				InputStream targetStream = new FileInputStream(emailexcelstorePath);
		
				fileStorageService.MoveCsvAndImgToArchive(targetStream, emailexcelstorePath.getName(), "2");
		
				emailService.sendRejectedDatamail(emailexcelstorePath);
			
				if(emailexcelstorePath.delete())
		        {
		            System.out.println("File deleted successfully From EDU");
		        }
		        else
		        {
		            System.out.println("Failed to delete the file From EDU");
		        }
				result="Added data successfully, Rejected Data sent via mail";
				
		
		
		
		
		
		}
		else
		{
			result="File is Empty/Blank";
		}
		
		Date date1 = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strdate1 = formatter1.format(date1);
		strdate1 = strdate1.replace(" ", "_");
		strdate1 = strdate1.replace(":", "-");

		csvnm = strdate1 + ".csv";
		S3Object moveddat = fileStore.readExcel(csvFileLocation);
		if(moveddat!=null) {
		InputStream mvcsvstream = moveddat.getObjectContent();
		if(mvcsvstream!=null){
//		fileStorageService.MoveCsvAndImgToArchive(mvcsvstream, csvnm, "1");
		

//		fileStore.deleteFile(fi.getKey());
		}
		}

		
		System.out.println("=========studentDataReviewList=============="+studentDataReviewList);
		
		
		// reader.close();
		long id = 0000;
		
 /*		HashMap<String, List<UniversityStudDocResponse>> resp = associateManagerService
				.AutosaveStudentInfo(studentDataReviewList, id);

		if (resp.get("RejectedData") != null && !resp.get("RejectedData").isEmpty()) {
			List<UniversityStudDocResponse> response = resp.get("RejectedData");

			XSSFWorkbook workbook = new XSSFWorkbook();

			// spreadsheet object
			XSSFSheet sheet = workbook.createSheet(" Student Data ");

			int rownum = 0;
			// XSSFRow row = null;
			Row row = sheet.createRow(0);

			Cell cell = row.createCell(0);
			cell.setCellValue("Degree");

			cell = row.createCell(1);
			cell.setCellValue("Semester");

			cell = row.createCell(2);
			cell.setCellValue("Seat No.");
			
			cell = row.createCell(3);
			cell.setCellValue("MonthOfPassing");

			cell = row.createCell(4);
			cell.setCellValue("YearOfPassing");

			cell = row.createCell(5);
			cell.setCellValue("Reasons");
			rownum++;
			for (UniversityStudDocResponse user : response) {
				row = sheet.createRow(rownum++);
				createList(user, row);
			}

			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String strdate = formatter.format(date);
			strdate = strdate.replace(" ", "_");
			strdate = strdate.replace(":", "-");

			if(awsORtest.equalsIgnoreCase("AWS")) {
//				String returnpath=fileStorageService.storeRejectedDataFile("Rejected_Data_" + strdate + ".csv");
//			    emailexcelstorePath = new File(returnpath);
			}
			else {
				
				emailexcelstorePath = new File("./Rejected_Csv/Rejected_Data_" + strdate + ".csv");

			}
			FileOutputStream out = new FileOutputStream(emailexcelstorePath);
			workbook.write(out);
			out.close();
			InputStream targetStream = new FileInputStream(emailexcelstorePath);

//			fileStorageService.MoveCsvAndImgToArchive(targetStream, emailexcelstorePath.getName(), "2");

//			emailService.sendRejectedDatamail(emailexcelstorePath);
		
			if(emailexcelstorePath.delete())
	        {
	            System.out.println("File deleted successfully From EDU");
	        }
	        else
	        {
	            System.out.println("Failed to delete the file From EDU");
	        }
			result="Added data successfully, Rejected Data sent via mail";

		}
		else {
			result="Added data succefully,No data is Rejected from this file";
		}
*/
		}
		else
		{
			result="File not found to read data";
			logger.info("********result********"+result);

		}
		logger.info("********result********"+result);

	
		String statusflag1="InActive";
		chkAutoReadingStatus.updateFlg(statusflag1);
			}
			else
			{
				result="Process already active!";
				logger.info("********result********"+result);

			}
			
		}
		} 
		
		
		if(awsORtest.equalsIgnoreCase("InHouse")) {

			Optional<checkautoReadingActiveEntity> chk= chkAutoReadingStatus.findById((long) 1);
			checkautoReadingActiveEntity ischk=chk.get();
			String chkflag = null;
			if(ischk!=null)
			{
				if(ischk.getFlag()!=null){
					chkflag=ischk.getFlag();
				}
			}
			if(chkflag!=null) {
			if(chkflag.equalsIgnoreCase("InActive")) {
				String statusflag="Active";
				chkAutoReadingStatus.updateFlg(statusflag);

		logger.info("********Enterning ExcelReadingScheduler readExcelFiles*****start***",new Date().getTime());
		List<AssociateExcelDataEntity> studentDataReviewList = new ArrayList<AssociateExcelDataEntity>();
		String imagefile = null;
		String imagefilenm=null;
		String previmagefile = "";
		
		File emailexcelstorePath = null;
		int k;
		int check = 0;
		String filePath = "ImageNotAvailable";
		String filePathStatus = "Not";
		String line;

	
		if(ftpConfiguration.isfileExists(csvFileLocation)==true) {
		InputStream csvstream = ftpConfiguration.readExcel(csvFileLocation);
		if(csvstream!=null) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(csvstream));

		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			if (check == 0) {
				check++;
				continue;
			}

			String[] datalist = line.split(",");
			logger.info("" + datalist);
			AssociateExcelDataEntity studentData = new AssociateExcelDataEntity();

			for (k = 0; k < datalist.length; k++) {

				if (k == 0) {
//					studentData.setStream(datalist[0]);
					studentData.setDegree(datalist[0]);
					
				} else if (k == 1) {
					studentData.setSemester(datalist[1]);

				} else if (k == 2) {
//					studentData.setEnrollmentNo(datalist[2]);
					studentData.setSeatNo(datalist[2]);

				} else if (k == 3) {
					studentData.setMonthOfPassing(datalist[3]);
				}
				else if (k == 4) {
//					studentData.setPassingYear(datalist[4]);
					studentData.setPassingYear(datalist[4]);

				} else if (k == 5) {
					imagefilenm = datalist[5];
					
					previmagefile = imagefilenm;
					studentData.setImageName(imagefilenm);
					
				

				}

			}
			studentDataReviewList.add(studentData);

		}
		
		logger.info("*******excel data save to table***",new Date().getTime());
		
		associateExcelDataRepository.daleteExcelData();
		
		associateExcelDataRepository.saveAll(studentDataReviewList);
		
		
		
		XSSFWorkbook workbook = new XSSFWorkbook();

		// spreadsheet object
		XSSFSheet sheet = workbook.createSheet(" Student Data ");

		int rownum = 0;
		// XSSFRow row = null;
		Row row = sheet.createRow(0);

		Cell cell = row.createCell(0);
		cell.setCellValue("Degree");

		cell = row.createCell(1);
		cell.setCellValue("Semester");

		cell = row.createCell(2);
		cell.setCellValue("Seat No.");
		
		cell = row.createCell(3);
		cell.setCellValue("MonthOfPassing");

		cell = row.createCell(4);
		cell.setCellValue("YearOfPassing");

		cell = row.createCell(5);
		cell.setCellValue("Reasons");
		rownum++;
		
		
		logger.info("*******send data to PROCEDURE***",new Date().getTime());
		
		List<Map<String ,Object>> excelCompareList = associateExcelDataRepository.excelCompare();
		
		logger.info("******* PROCEDURE return data ***",new Date().getTime());
		
		final ObjectMapper mapper = new ObjectMapper();
		
		List<ExcelDataResponse> rejectList = new ArrayList<>();
		List<ExcelDataResponse> selectedList = new ArrayList<>();
		
		List<UniversityStudentDocument> studentDataList = new ArrayList<UniversityStudentDocument>();
		
		
		logger.info("*******img read start ***",new Date().getTime());
		for(int i=0; i<excelCompareList.size(); i++) {
			final ExcelDataResponse excelDataResponse = mapper.convertValue(excelCompareList.get(i), ExcelDataResponse.class);
	//		logger.info(String.valueOf(i) + "-->"+excelDataResponse);
			
			
			if(excelDataResponse.getStatus().equalsIgnoreCase("selected")){

			imagefile=imgLocation;
	//		logger.info("imageFileNAme" + imagefile);

			String flag = "2";
			
			imagefilenm = excelDataResponse.getImage_name();

			Date date1 = new Date();
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String strdate1 = formatter1.format(date1);
			strdate1 = strdate1.replace(" ", "_");
			strdate1 = strdate1.replace(":", "-");
				if (!previmagefile.equals(imagefilenm)) {
					InputStream imagestream = ftpConfiguration.readExcel(imagefile+imagefilenm);
					if (imagestream != null) {
	
						filePath = fileStorageService.storeScanFileOnFtp(imagestream, flag, imagefilenm);

						String filename = imagefilenm.split("\\.")[0];
						String extension = imagefilenm.split("\\.")[1];

						String newimagefilenm = filename+"_" + strdate1 +  "." + extension;
						fileStorageService.storeScanFileOnFtpToArchive(imagestream, newimagefilenm, "1");

						filePathStatus = "";
					} else {
						filePath = "ImageNotAvailable";
						filePathStatus = "Not";
					}
				}
					previmagefile = imagefilenm;
					excelDataResponse.setImage_name(filePath);
					excelDataResponse.setStatus(filePathStatus.equalsIgnoreCase("Not") ? "Reject" : "selected" );
					excelDataResponse.setMassage("Image"+filePathStatus+"Available");
				
			}
			
		//	System.out.println("excelDataResponse.================"+excelDataResponse.getStatus());
			
			
			if(excelDataResponse.getStatus().equalsIgnoreCase("Reject")){
				
				row = sheet.createRow(rownum++);
				createListNew(excelDataResponse, row);
				
			}else {
			
				long id = 0000;
				
				UniversityStudentDocument studentData=new UniversityStudentDocument();

		        studentData.setStreamId(Long.parseLong(excelDataResponse.getDegree()));	
		        studentData.setEnrollmentNo(excelDataResponse.getSeat_no());
		        studentData.setPassingYearId(Long.parseLong(excelDataResponse.getPassing_year()));	
		        studentData.setOriginalDOCuploadfilePath(excelDataResponse.getImage_name());
		        studentData.setSemId(Long.parseLong(excelDataResponse.getSemester()));
		       // studentData.setBranchId(branch.getId());
		        studentData.setCreatedate(new Date());
		        studentData.setCreateby("000");
		        studentData.setMonthOfPassing(excelDataResponse.getMonth_of_passing());
				
				
				studentDataList.add(studentData);
			}
				
		}
		
		logger.info("*******img read End ***",new Date().getTime());
		
		// save 
		universityStudentDocRepository.saveAll(studentDataList);
//		
				
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
				String strdate = formatter.format(date);
				strdate = strdate.replace(" ", "_");
				strdate = strdate.replace(":", "-");
		
				if(awsORtest.equalsIgnoreCase("AWS")) {
					String returnpath=fileStorageService.storeRejectedDataFile("Rejected_Data_" + strdate + ".csv");
				    emailexcelstorePath = new File(returnpath);
				}
				else {
					
					emailexcelstorePath = new File("./Rejected_Csv/Rejected_Data_" + strdate + ".csv");
		
				}
				FileOutputStream out = new FileOutputStream(emailexcelstorePath);
				workbook.write(out);
				out.close();
				InputStream targetStream = new FileInputStream(emailexcelstorePath);
		
				fileStorageService.storeScanFileOnFtpToArchive(targetStream, emailexcelstorePath.getName(), "2");
		
				emailService.sendRejectedDatamail(emailexcelstorePath);
			
				if(emailexcelstorePath.delete())
		        {
		            System.out.println("File deleted successfully From EDU");
		        }
		        else
		        {
		            System.out.println("Failed to delete the file From EDU");
		        }
				result="Added data successfully, Rejected Data sent via mail";
				
		
		
		
		
		
		}
		else
		{
			result="File is Empty/Blank";
		}
		
		}
		else
		{
			result="File not found to read data";
			logger.info("********result********"+result);

		}
		logger.info("********result********"+result);

	
		String statusflag1="InActive";
		chkAutoReadingStatus.updateFlg(statusflag1);
			}
			else
			{
				result="Process already active!";
				logger.info("********result********"+result);

			}
			
		}
		} 
		
		logger.info("*******All End ***",new Date().getTime());
		
		}
		catch(Exception e) {
			String statusflag1="InActive";
			result="Unable to read file,check file format/Data and try again!";
			chkAutoReadingStatus.updateFlg(statusflag1);
		}
		logger.info("******** Exiting ExcelReadingScheduler readExcelFiles********");


		return result;

	}
	
	
	
	
	
	
	
	

	private static void createList(UniversityStudDocResponse user, Row row) // creating cells for each row
	{
		Cell cell = row.createCell(0);
		cell.setCellValue(user.getStream());

		cell = row.createCell(1);
		cell.setCellValue(user.getSemester());

		cell = row.createCell(2);
		cell.setCellValue(user.getEnrollmentNo());

		cell = row.createCell(3);
		cell.setCellValue(user.getMonthOfPassing());
		
		cell = row.createCell(4);
		cell.setCellValue(user.getPassingYear());

		cell = row.createCell(5);
		cell.setCellValue(user.getReason());

	}
	
	
	private static void createListNew(ExcelDataResponse user, Row row) // creating cells for each row
	{
		Cell cell = row.createCell(0);
		cell.setCellValue(user.getDegree());

		cell = row.createCell(1);
		cell.setCellValue(user.getSemester());

		cell = row.createCell(2);
		cell.setCellValue(user.getSeat_no());

		cell = row.createCell(3);
		cell.setCellValue(user.getMonth_of_passing());
		
		cell = row.createCell(4);
		cell.setCellValue(user.getPassing_year());

		cell = row.createCell(5);
		cell.setCellValue(user.getMassage());

	}

}
