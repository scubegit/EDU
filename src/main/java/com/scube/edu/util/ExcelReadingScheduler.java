package com.scube.edu.util;

import java.io.BufferedReader;
import java.io.File;
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
import com.scube.edu.awsconfig.FileStore;
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
	private FileStorageService fileStorageService;

	@Autowired
	private EmailService emailService;

	@Value("${excel.file.path}")
    private String excelFileLocation;
	
	@Value("${img.path}")
    private String imgLocation;
	
	@Value("${excel.img.moved.dir}")
    private String excelImgmovedDir;
	
	@Value("${rejected.excel.store.dir}")
    private String rejectedExcelStoreDir;
	
	
	
	@Scheduled(cron = "${cron.time.excelRead}")
	public String readExcelData() throws Exception, Exception {
		logger.info("********ExcelReadingScheduler readExcelData********");

		List<String> ExcelFile = new ArrayList<>();
		File excelFilePath = new File(excelFileLocation);
			
		S3Object fi=fileStore.readExcel();
		
		try {
			/*
			 * File[] listofExcelFiles = excelFilePath.listFiles(); String filnmPath = "";
			 * String filnm; if (listofExcelFiles != null) { for (File file :
			 * listofExcelFiles) { if (file.isFile()) { filnm = file.getName(); if
			 * (filnm.endsWith(".xlsx")) { filnmPath = file.getAbsolutePath();
			 * ExcelFile.add(filnmPath); } } } }
			 */
			InputStream is = fi.getObjectContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;
			System.out.println("--File content:");
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	//	return readEscelFiles(ExcelFile);
			return null;
	}

	@Value("${file.awsORtest}")
	private String awsORtest;

	public String readEscelFiles(List<String> listOfExcelfiles) throws Exception, IOException {
		logger.info("********ExcelReadingScheduler readEscelFiles********");
		List<UniversityStudDocResponse> studentDataReviewList = new ArrayList<UniversityStudDocResponse>();
		String imagefile = null;
		String previmagefile = "";

		File imagePath = null;
		File file = null;
		File emailexcelstorePath = null;
		if (listOfExcelfiles != null) {

			for (int j = 0; j < listOfExcelfiles.size(); j++) {
				String path = listOfExcelfiles.get(j);
				file = new File(path);

				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet worksheet = workbook.getSheetAt(0);
				int rowcnt = worksheet.getLastRowNum();
				int clomncnt = worksheet.getRow(0).getLastCellNum();

				int noOfColumns = worksheet.getRow(0).getPhysicalNumberOfCells();

				logger.info("noOfColumns=" + noOfColumns);

				logger.info("clomncnt=" + clomncnt);
				String fileSubPath = "file/";
				String filePath = null;

				if (clomncnt == 5) {
					for (int i = 1; i <= rowcnt; i++) {

						XSSFRow row = worksheet.getRow(i);

						UniversityStudDocResponse studentData = new UniversityStudDocResponse();
						if (row != null) {
							int rowcell = row.getPhysicalNumberOfCells();
							logger.info("rowcell=" + rowcell);
							if (row.getCell(0) != null) {

								studentData.setStream(row.getCell(0).getStringCellValue());
							} else {
								studentData.setStream("");
							}

							if (row.getCell(1) != null) {

								studentData.setSemester(row.getCell(1).getStringCellValue());
							} else {
								studentData.setSemester("");
							}

							if (row.getCell(2) != null) {
								int cellType = row.getCell(2).getCellType();

								logger.info("celltype " + cellType);
								if (cellType == 1) {
									studentData.setEnrollmentNo(row.getCell(2).getStringCellValue());
								} else {
									Integer enrollNo = (int) row.getCell(2).getNumericCellValue();
									studentData.setEnrollmentNo(enrollNo.toString());
								}
							} else {
								studentData.setEnrollmentNo("");
							}

							if (row.getCell(3) != null) {
								int yearcellType = row.getCell(3).getCellType();

								if (yearcellType == 1) {
									studentData.setPassingYear(row.getCell(3).getStringCellValue());
								} else {
									Integer yearofPassing = (int) row.getCell(3).getNumericCellValue();
									studentData.setPassingYear(yearofPassing.toString());
								}
							} else {
								studentData.setPassingYear("");
							}

							if (row.getCell(4) != null) {

								imagefile = row.getCell(4).getStringCellValue();
								imagePath = new File(imgLocation + imagefile);

								String flag = "2";

								if (!previmagefile.equals(imagefile)) {
									boolean val = imagePath.exists();
									if (val == true) {
										if (awsORtest.equalsIgnoreCase("TEST") || awsORtest.equalsIgnoreCase("LOCAL")) {
											filePath = fileStorageService.storeschedularFile(imagePath, fileSubPath,
													flag);
										} else {
											// filePath = fileStorageService.storeFileOnAws(excelFilePath , flag);
										}
										
										Path sourceFilePath = imagePath.toPath();
										File dest = new File(excelImgmovedDir + imagefile);
									//	Files.move(sourceFilePath, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

									} else {
										filePath = "ImageNotAvailable";
									}
									
								}
								previmagefile = imagefile;

								studentData.setOriginalDOCuploadfilePath(filePath);
							}
							studentDataReviewList.add(studentData);

						}
					}
				}

				workbook.close();

				
				File dest1 = new File(excelImgmovedDir + file.getName());
				//Files.move(file.toPath(), dest1.toPath(), StandardCopyOption.REPLACE_EXISTING);

			}
		}
		long id = 0000;
		HashMap<String, List<UniversityStudDocResponse>> resp = associateManagerService
				.saveStudentInfo(studentDataReviewList, id);

		if (resp.get("RejectedData") != null) {
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
			cell.setCellValue("YearOfPassing");

			cell = row.createCell(4);
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

			// This data needs to be written (Object[])
			emailexcelstorePath = new File(rejectedExcelStoreDir+"Rejected_Data_" + strdate + ".xlsx");
			FileOutputStream out = new FileOutputStream(emailexcelstorePath);

			workbook.write(out);
			out.close();
		}

		// emailService.sendRejectedDatamail(emailexcelstorePath);
		return null;

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
		cell.setCellValue(user.getPassingYear());

		cell = row.createCell(4);
		cell.setCellValue(user.getReason());

	}

}
