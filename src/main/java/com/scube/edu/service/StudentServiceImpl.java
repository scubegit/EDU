package com.scube.edu.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.mapping.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scube.edu.awsconfig.BucketName;
import com.scube.edu.awsconfig.FileStore;
import com.scube.edu.model.BranchMasterEntity;
import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.PriceMaster;
import com.scube.edu.model.RequestTypeMaster;
import com.scube.edu.model.SemesterEntity;
import com.scube.edu.model.StreamMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.model.VerificationDocView;
import com.scube.edu.model.VerificationRequest;
import com.scube.edu.repository.PriceMasterRepository;
import com.scube.edu.repository.UserRepository;
import com.scube.edu.repository.VerificationDocViewRepository;
import com.scube.edu.repository.VerificationRequestRepository;
import com.scube.edu.repository.YearOfPassingRepository;
import com.scube.edu.request.StudentDocEditVerificationRequest;
import com.scube.edu.request.StudentDocVerificationRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.request.paymensReqFlagRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.CollegeResponse;
import com.scube.edu.response.PriceMasterResponse;
import com.scube.edu.response.RequestTypeResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.VerificationListPojoResponse;
import com.scube.edu.response.VerificationResponse;
import com.scube.edu.response.JwtResponse;
import com.scube.edu.response.StreamResponse;

import com.scube.edu.security.JwtUtils;
import com.scube.edu.util.FileStorageService;
import com.scube.edu.util.StringsUtils;

import lombok.AllArgsConstructor;


@Service
public class StudentServiceImpl implements StudentService{

private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);


	
	
	BaseResponse	baseResponse	= null;  
	
//	private final FileStore fileStore;
      
	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 
	 @Autowired
	 FileStore fileStore;
	 
	 @Autowired
	 VerificationRequestRepository verificationReqRepository;
	 
	 @Autowired
	 PriceMasterRepository priceMasterRepo;
	 
	 @Autowired
	 YearOfPassingRepository yearOfPassRepo;
	 
	@Autowired
	DocumentService	documentService;
	 
	 @Autowired
	VerificationRequestRepository verificationReqRepo;
	 
	 @Autowired
	 VerificationDocViewRepository veriDocViewRepo;
	 
	 @Autowired
	 YearOfPassingService yearOfPassService;
	 
	 @Autowired
	 private FileStorageService fileStorageService;
	 
	 @Autowired 
	 StreamService streamService;
	 
	 @Autowired 
	 RequestTypeService reqTypeService;
	 
	 @Autowired 
	 CollegeSevice collegeSevice;
	 
	 @Autowired
	 SemesterService semesterService;
		
	 @Autowired
	 BranchMasterService branchMasterService;
		
	 @Autowired
	 UserRepository userRepository;
	 
	 @Override

		public List<VerificationResponse> getVerificationDocsDataByUserid(long userId) throws Exception {

		 
		 logger.info("********StudentServiceImpl getVerificationDataByUserid********");
		 
		 	List<VerificationResponse> List = new ArrayList<>();
		 	
		 	List<VerificationRequest> verReq = verificationReqRepository.findByUserId(userId);
			
			System.out.println("---------"+ verReq);
			
			for(VerificationRequest req: verReq) {
				
				System.out.println("verReq---"+ req.getDocumentId());
				
				VerificationResponse studentVerificationList = new VerificationResponse();
				
				PassingYearMaster year = yearOfPassService.getYearById(req.getYearOfPassingId());
				
				DocumentMaster doc = documentService.getNameById(req.getDocumentId());
				
				RequestTypeResponse reqMaster = reqTypeService.getNameById(req.getRequestType());
				
				StreamMaster stream = streamService.getNameById(req.getStreamId());
				
				SemesterEntity sem=semesterService.getSemById(req.getSemId());
				
				BranchMasterEntity branch=branchMasterService.getbranchById(req.getBranchId());
				
				
				if(req.getRequestType() != null) {
				RequestTypeResponse request = reqTypeService.getNameById(req.getRequestType());
				studentVerificationList.setRequest_type_id(request.getId());
				studentVerificationList.setRequest_type(request.getRequestType());
				}
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
				String strDate= formatter.format(req.getCreatedate());
				
				studentVerificationList.setDoc_status(req.getDocStatus());
				studentVerificationList.setId(req.getId());
				studentVerificationList.setApplication_id(req.getApplicationId());
//				closedDocResp.setCollege_name_id(req.getCollegeId());
				studentVerificationList.setDoc_name(doc.getDocumentName()); //
				studentVerificationList.setEnroll_no(req.getEnrollmentNumber());
				studentVerificationList.setFirst_name(req.getFirstName());
				studentVerificationList.setLast_name(req.getLastName());
				
////				studentVerificationList.setRequest_type_id(req.get);
//				studentVerificationList.setStream_id(req.getStreamId());
//				studentVerificationList.setUni_id(req.getUniversityId());
				studentVerificationList.setUser_id(req.getUserId());
				studentVerificationList.setVer_req_id(req.getVerRequestId());
				studentVerificationList.setYear(year.getYearOfPassing());	
				studentVerificationList.setUpload_doc_path(req.getUploadDocumentPath());
				studentVerificationList.setStream_name(stream.getStreamName());
				studentVerificationList.setReq_date(strDate);
//				studentVerificationList.setRequest_type_id(reqMaster.getId());
				studentVerificationList.setBranch_nm(branch.getBranchName());
				studentVerificationList.setSemester(sem.getSemester());
				studentVerificationList.setMonthOfPassing(req.getMonthOfPassing());
				
				List.add(studentVerificationList);
			}
			
			return List;
			
//			List<VerificationListPojoResponse> verDocRespPojo = new ArrayList<VerificationListPojoResponse>();
//			final ObjectMapper mapper = new ObjectMapper();
//			for(int i=0; i<verReq.size();i++) {
//				final VerificationListPojoResponse pojo = mapper.convertValue(verReq.get(i), VerificationListPojoResponse.class);
//				System.out.println(pojo);
//				verDocRespPojo.add(pojo);
//			}

				

		
		 
	 }

	@Override
	public List<VerificationResponse> getClosedRequests(long userId) {
		
		logger.info("********StudentServiceImpl getClosedRequests********"+ userId);
		
		List<VerificationResponse> List = new ArrayList<>();
		
		List<VerificationRequest> closedReqs = verificationReqRepository.findByStatusAndUserId(userId);
		System.out.println("---------------------"+ closedReqs);
		
		for(VerificationRequest req: closedReqs) {
			
			VerificationResponse closedDocResp = new VerificationResponse();
			
			PassingYearMaster year = yearOfPassService.getYearById(req.getYearOfPassingId());
			
			DocumentMaster doc = documentService.getNameById(req.getDocumentId());
			
			StreamMaster stream = streamService.getNameById(req.getStreamId());
			
			SemesterEntity sem=semesterService.getSemById(req.getSemId());
			
			BranchMasterEntity branch=branchMasterService.getbranchById(req.getBranchId());
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
			String strDate= formatter.format(req.getCreatedate());
			
			if(req.getRequestType() != null) {
			RequestTypeResponse request = reqTypeService.getNameById(req.getRequestType());
			closedDocResp.setRequest_type_id(request.getId());
			}
			
			closedDocResp.setDoc_status(req.getDocStatus());
			closedDocResp.setId(req.getId());
			closedDocResp.setApplication_id(req.getApplicationId());
//			closedDocResp.setCollege_name_id(req.getCollegeId());
			closedDocResp.setDoc_name(doc.getDocumentName());
			closedDocResp.setEnroll_no(req.getEnrollmentNumber());
			closedDocResp.setFirst_name(req.getFirstName());
			closedDocResp.setLast_name(req.getLastName());
////			closedDocResp.setRequest_type_id(req.get);
//			closedDocResp.setStream_id(req.getStreamId());
//			closedDocResp.setUni_id(req.getUniversityId());
			closedDocResp.setUser_id(req.getUserId());
			closedDocResp.setVer_req_id(req.getVerRequestId());
			closedDocResp.setYear(year.getYearOfPassing());	
			closedDocResp.setStream_name(stream.getStreamName());
			closedDocResp.setReq_date(strDate);
			closedDocResp.setSemester(sem.getSemester());
			closedDocResp.setBranch_nm(branch.getBranchName());
			closedDocResp.setMonthOfPassing(req.getMonthOfPassing());
			List.add(closedDocResp);
			
		}
		
		return List;
	}

	@Override
	public HashMap<String, List<Long>> saveVerificationDocAndCalculateAmount(List<StudentDocVerificationRequest> studentDocReq, HttpServletRequest request) {
		
		logger.info("********Enterning StudentServiceImpl saveVerificationDocAndCalculateAmount********");
		logger.info("********Enterning StudentServiceImpl ********"+studentDocReq);
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		long total;
		long totalWithGST;
		
		long amtWithoutGST = 0;
		long amtWithGST = 0;
		
		// assign application_id here
		Long appId = (long) 0;
		Long app_id;
		app_id = verificationReqRepo.getMaxApplicationId();
		logger.info("---------"+ app_id);
		
		if(app_id == null) {
			appId = (long) 1;
			System.out.println("here");
		}else {
			appId = app_id + 1;
		}
		
		logger.info("---------"+ appId);
		
		Long userid=null;
		boolean flag=false;
		List<VerificationRequest> list = new ArrayList<>();
		Long ver_req = (long) 1;
		for(StudentDocVerificationRequest req : studentDocReq) {
			
			logger.info("********Enterning req ********"+req);
			
			logger.info("Entring in loop to save records= "+req.getUserid());
			userid=req.getUserid();
			System.out.println(req);
			VerificationRequest resp = new VerificationRequest();
			
			Long assign_to = (long) 0;
			System.out.println("------In Save and calculate Req FOR LOOP----");
			
			PriceMaster diff =  priceMasterRepo.getPriceByYearDiff(year , req.getYearofpassid(),req.getRequesttype(),Long.parseLong(req.getDocname()));
			
			// diff fetches * from pricemaster where year diff is between year_range_start and year_range_end
			
			total = diff.getTotalAmt();
			totalWithGST =    ((diff.getSecurCharge() * diff.getGst()) / 100) + diff.getTotalAmt();
			
			amtWithoutGST += diff.getTotalAmt();
			amtWithGST += ((diff.getSecurCharge() * diff.getGst()) / 100) + diff.getTotalAmt();
			
			logger.info(total+ "   "+ totalWithGST+ "   "+ amtWithoutGST + "   "+ amtWithGST);
			
			// Make a verificationRequestRepo function which will save total and totalWithoutGST against each application id
			resp.setDocUniAmt(diff.getAmount());					//
			resp.setDocSecurCharge(diff.getSecurCharge());			//
			resp.setApplicationId(appId);
			resp.setAssignedTo(assign_to);
			resp.setCollegeId(req.getCollegenameid());
			resp.setDocStatus("Requested");
			resp.setDocumentId(req.getDocname());
			resp.setEnrollmentNumber(req.getEnrollno());
			resp.setFirstName(req.getFirstname());
			resp.setLastName(req.getLastname());
			resp.setIsdeleted("N");
			resp.setStreamId(req.getStreamid());
			resp.setUniversityId((long) 1);
			resp.setUploadDocumentPath(req.getFilepath());
			resp.setUserId(req.getUserid());
			resp.setVerRequestId(ver_req);
			resp.setRequestType(req.getRequesttype());
			resp.setYearOfPassingId(String.valueOf(req.getYearofpassid()));
			resp.setPaymentId(req.getPaymentId());
			resp.setDocAmt(total);							//with out gst amount
			resp.setEmbassyid(req.getEmbassyid());
			resp.setEmbassyname(req.getEmbassyname());
			resp.setEmbassyadress(req.getEmbassyaddress());
			
			 flag=checkGStExemption(userid);

			if(flag==true)
				resp.setDosAmtWithGst(total);//if added so as gst not to be taken for
			else
			resp.setDosAmtWithGst(totalWithGST);			 
			
			
			
			
			resp.setBranchId(req.getBranchId());
			resp.setSemId(req.getSemId());
			resp.setCreateby(req.getCreateby());
			resp.setMonthOfPassing(req.getMonthOfPassing());
			
			

			if(req.getAltEmail()!=null) {
			if(!req.getAltEmail().equalsIgnoreCase("")){
				resp.setAltEmail(req.getAltEmail());
			}
			}
			ver_req += 1;
			
			list.add(resp);
			logger.info("Exiting for loop= " +req.getUserid());
			logger.info("Exiting for loop= " +list.add(resp));
		}
		HashMap<String, List<Long>> map = new HashMap<String, List<Long>>();
		
		List<Long> amtwithoutGst = new ArrayList<>();
		amtwithoutGst.add(amtWithoutGST);
				
		
		List<Long> listamtwithGst = new ArrayList<>();
		//for gst changes
		if(flag==true)  //if added so as gst not to be taken for
			listamtwithGst.add(amtWithoutGST);
		else
		listamtwithGst.add(amtWithGST);
		
		
		
		map.put("total_without_gst", amtwithoutGst);
		map.put("total_with_gst", listamtwithGst);
		
		
		List<VerificationRequest>returnDta=verificationReqRepo.saveAll(list);
		List<Long> idList=new ArrayList<>();

		for(VerificationRequest data: returnDta)
		{
			Long id=data.getId();
			idList.add(id);
			
		}
		map.put("saveDocID", idList);
		logger.info("saveDocID------------"+ idList);

		logger.info("added request successfully="+(new Date())+ "User Id= "+userid);
		
		return map;
	}

	@Override
	public HashMap<String, Long> saveStudentSingleVerificationDoc(StudentDocVerificationRequest studentDocReq,
			HttpServletRequest request) {
		
		System.out.println("********StudentServiceImpl saveStudentSingleVerificationDoc********"+ studentDocReq.getDocname());
		
		VerificationRequest verDoc = new VerificationRequest();
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		long total;
		long totalWithGST;
		
		long amtWithoutGST = 0;
		long amtWithGST = 0;
		
		// assign application_id here
		Long appId = (long) 0;
		Long app_id;
		app_id = verificationReqRepo.getMaxApplicationId();
		logger.info("---------"+ app_id);
		
		if(app_id == null) {
			appId = (long) 1;
			System.out.println("here");
		}else {
			appId = app_id + 1;
		}
		
		logger.info("---------"+ appId);
		boolean flag=checkGStExemption(studentDocReq.getUserid());
		
		PriceMaster diff =  priceMasterRepo.getPriceByYearDiff(year , studentDocReq.getYearofpassid(),studentDocReq.getRequesttype(),Long.parseLong(studentDocReq.getDocname()));
		
		logger.info("---from price master year----"+ year+ "-total amount-"+diff.getTotalAmt()+"-gst-"+diff.getGst()+"--");

		
		
		total = diff.getTotalAmt();
		totalWithGST =    ((diff.getTotalAmt() * diff.getGst()) / 100) + diff.getTotalAmt();
		
		verDoc.setApplicationId(appId);
		verDoc.setAssignedTo((long)0);
		verDoc.setCollegeId(studentDocReq.getCollegenameid());
		verDoc.setCreateby(String.valueOf(studentDocReq.getUserid()));
		verDoc.setDocAmt(total);
		verDoc.setDocStatus("Requested");
		verDoc.setDocumentId(studentDocReq.getDocname());
		
		if(flag==true)
			verDoc.setDosAmtWithGst(total);	
		else
		verDoc.setDosAmtWithGst(totalWithGST);											//
		
		
		verDoc.setEnrollmentNumber(studentDocReq.getEnrollno());
		verDoc.setFirstName(studentDocReq.getFirstname());
		verDoc.setLastName(studentDocReq.getLastname());
		verDoc.setIsdeleted("N");
		verDoc.setStreamId(studentDocReq.getStreamid());
		verDoc.setUniversityId((long) 1);
		verDoc.setUploadDocumentPath(studentDocReq.getFilepath());
		verDoc.setVerRequestId((long) 1);
		verDoc.setYearOfPassingId(String.valueOf(studentDocReq.getYearofpassid()));
		verDoc.setUserId(studentDocReq.getUserid());
		verDoc.setEmbassyid(studentDocReq.getEmbassyid());
		verDoc.setEmbassyname(studentDocReq.getEmbassyname());
		verDoc.setEmbassyadress(studentDocReq.getEmbassyaddress());
		verificationReqRepo.save(verDoc);
		
		HashMap<String, Long> map = new HashMap<String, Long>();
		
		map.put("total_without_gst", total);
		
		
		
		if(flag==true)
			map.put("total_with_gst", total);
		else
				map.put("total_with_gst", totalWithGST);
		
		return map;
//		return null;
	}
	
	@Value("${file.awsORtest}")
    private String awsORtest;
	
	public String saveDocument (MultipartFile file) {
		String fileSubPath = "file/";
		String flag = "1";
		String filePath;
		if(awsORtest.equalsIgnoreCase("TEST") || awsORtest.equalsIgnoreCase("LOCAL")) {
		filePath = fileStorageService.storeFile(file , fileSubPath, flag);
		}
		else if(awsORtest.equalsIgnoreCase("InHouse") ) {
			 filePath = fileStorageService.storeFileOnFtp(file , flag);	//FTP	
			 }
		else {
		filePath = fileStorageService.storeFileOnAws(file, flag);    //For AWS
		}
		logger.info("---------StudentServiceImpl saveDocument----------------");
		
        
        
        
		
		return filePath;
		
	}

	@Override
	public HashMap<String,List< Long>> CalculateDocAmount(List<StudentDocVerificationRequest> studentDocReq){
		
	logger.info("********StudentServiceImpl calculateTotalAmount********");
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		long total;
		long totalWithGST;
		
		long amtWithoutGST = 0;
		long amtWithGST = 0;
		
		// assign application_id here
		Long appId = (long) 0;
		Long app_id;
		app_id = verificationReqRepo.getMaxApplicationId();
		logger.info("---------"+ app_id);
		
		if(app_id == null) {
			appId = (long) 1;
			System.out.println("here");
		}else {
			appId = app_id + 1;
		}
		
		logger.info("---------"+ appId);
		HashMap<String,List<Long>> map = new HashMap<String,List<Long>>();

		List<Long> amtwithgst=new ArrayList<>();
		List<Long> amtwithoutgst=new ArrayList<>();
		List<Long> pricenotforYear=new ArrayList<>();

		Long userid = null;
		for(StudentDocVerificationRequest req : studentDocReq) {
			System.out.println(req);
			VerificationRequest resp = new VerificationRequest();
			
			System.out.println("------In Save and calculate Req FOR LOOP----");
			
			logger.info("Entring in loop to calculate payment= "+req.getUserid());
			userid=req.getUserid();
			PriceMaster diff =  priceMasterRepo.getPriceByYearDiff(year , req.getYearofpassid(),req.getRequesttype(),Long.parseLong(req.getDocname()));
			if (diff!=null)	{		
			total = diff.getTotalAmt();
			totalWithGST =    ((diff.getSecurCharge() * diff.getGst()) / 100) + diff.getTotalAmt();
			
			amtWithoutGST += diff.getTotalAmt();
			amtWithGST += ((diff.getSecurCharge() * diff.getGst()) / 100) + diff.getTotalAmt();
			
			logger.info(total+ " and  "+ totalWithGST+ "  and  "+ amtWithoutGST + " and   "+ amtWithGST);	
			}
			else {
				Optional<PassingYearMaster> dyear=yearOfPassRepo.findById(req.getYearofpassid());
				PassingYearMaster passingYr=dyear.get();
				pricenotforYear.add(Long.valueOf(passingYr.getYearOfPassing()));
				//map.put("total_without_gst", (long) 99999);
				//map.put("total_with_gst", (long) 99999);

			}
		}
	
		if(!pricenotforYear.isEmpty()&&pricenotforYear!=null) {
			
			amtwithgst.add((long) 99999);
			amtwithoutgst.add((long) 99999);
			
			map.put("total_without_gst", amtwithoutgst);
			map.put("total_with_gst", amtwithgst);			
			map.put("YearNotDefined", pricenotforYear);
			
		}else{
			amtwithgst.add(amtWithGST);
			amtwithoutgst.add(amtWithoutGST);
			
			map.put("total_without_gst", amtwithoutgst);
			map.put("total_with_gst", amtwithgst);
		
			//Added by mayuri
			
			UserMasterEntity entities= userRepository.findById(userid).get();
			System.out.print("-----------Role Id----------- " + entities.getRoleId());
			System.out.print("-----------Gove Body---------" + entities.getAreYouGov());
			System.out.print("-----------GST Exemption---------" + entities.getGstExemption());
		//	System.out.println("---------Embassy Id -----------"+ entities.getem)
			long val = 2;
		
			boolean flag=checkGStExemption(userid);
			
			if(flag==true)
				map.put("total_with_gst", amtwithoutgst);
			else {
				map.put("total_with_gst", amtwithgst);
			}
			
			
			
			
			/*
			 * if (entities.getRoleId().equals(val)) {
			 * 
			 * if (entities.getAreYouGov().equals("Y") &&
			 * entities.getGstExemption().equals("Y")) {
			 * 
			 * map.put("total_with_gst", amtwithoutgst);
			 * System.out.print("I am in if (total_without_gst)" + amtwithoutgst); }else{
			 * 
			 * map.put("total_with_gst", amtwithgst); System.out.print("I am in Else" +
			 * amtwithgst); }
			 * 
			 * }else{
			 * 
			 * map.put("total_with_gst", amtwithgst); System.out.print("I am in Else" +
			 * amtwithgst); }
			 */
			
			
			
			
		}
		
		/*
		 * } catch(Exception e) { map.put("total_without_gst", (long) 99999);
		 * map.put("total_with_gst", (long) 99999); }
		 */
		logger.info("Payment calculate Done= "+map+" UserId= "+userid);

		return map;		
	}
	
	@Override
	public String UpdatePaymentFlag(List<paymensReqFlagRequest> id) {
		
		logger.info("Array"+id);
		Integer rowcnt = 0;
		for (paymensReqFlagRequest list:id ) {
			 verificationReqRepo.updatePaymentFlag(list.getId());
			 rowcnt++;
		}
		 String row=Integer.toString(rowcnt);
		return row;
	}

	@Override
	public VerificationResponse getVerificationRequestById(long id) {
		
		logger.info("*****StudentServiceImpl getVerificationRequestById*****"+ String.valueOf(id));
		
		VerificationRequest req = verificationReqRepo.findById(id);
		
		VerificationResponse resp = new VerificationResponse();
		
		PassingYearMaster year = yearOfPassService.getYearById(req.getYearOfPassingId());
		
		DocumentMaster doc = documentService.getNameById(req.getDocumentId());
		
		RequestTypeResponse reqMaster = reqTypeService.getNameById(req.getRequestType());
		
		StreamMaster stream = streamService.getNameById(req.getStreamId());
		
		SemesterEntity sem=semesterService.getSemById(req.getSemId());
		
		BranchMasterEntity branch=branchMasterService.getbranchById(req.getBranchId());
		
		CollegeResponse college = collegeSevice.getNameById(req.getCollegeId());
		
		resp.setApplication_id(req.getApplicationId());
		resp.setBranch_nm(branch.getBranchName());
		resp.setCollege_name(college.getCollegeName());
		resp.setCollege_name_id(req.getCollegeId());
		resp.setDoc_name(doc.getDocumentName());
		resp.setDoc_status(req.getDocStatus());
		resp.setEnroll_no(req.getEnrollmentNumber());
		resp.setFirst_name(req.getFirstName());
		resp.setId(req.getId());
		resp.setLast_name(req.getLastName());
		resp.setMonthOfPassing(req.getMonthOfPassing());
//		resp.setOriginalDocUploadFilePath(req.getUploadDocumentPath());
		resp.setRemark(req.getRemark());
		resp.setRequest_type_id(reqMaster.getId());
		resp.setSemester(sem.getSemester());
		resp.setSemid(sem.getId());
		resp.setStream_id(req.getStreamId());
		resp.setStream_name(stream.getStreamName());
		resp.setUpload_doc_path(req.getUploadDocumentPath());
		resp.setUser_id(req.getUserId());
		resp.setYear_of_pass_id(Long.valueOf(req.getYearOfPassingId()));
		resp.setYear(year.getYearOfPassing());
		resp.setBranch_id(branch.getId());
		resp.setDoc_id(doc.getId());
		
		String embassyIdString = req.getEmbassyid(); 
		Long embassyId = Long.parseLong(embassyIdString);
		resp.setEmbassyid(embassyId);
		System.out.println("embasyyy"+embassyId + resp);
		//resp.setEmbassyid(req.getEmbassyid());
		resp.setEmbassyname(req.getEmbassyname());
		resp.setEmbassyaddress(req.getEmbassyadress());
	
		if(req.getEditReason() != null) {
			String temp = req.getEditReason();
			String[] list = temp.split("%");
			resp.setEditReason(list[list.length - 1]);
		}
		if(req.getAltEmail() != null) {
			resp.setAlt_email(req.getAltEmail());
		}
		
		return resp;
	}

	@Override
	public boolean editVerificationRequest(StudentDocEditVerificationRequest stuVerReq) {
		
		logger.info("*****StudentServiceImpl editVerificationRequest*****"+ stuVerReq.getId());
		
		VerificationRequest verReq = verificationReqRepo.findById(stuVerReq.getId());
		
		verReq.setBranchId(stuVerReq.getBranchid());
		verReq.setCollegeId(stuVerReq.getCollegenameid());
		verReq.setDocStatus("Requested");
		verReq.setDocumentId(stuVerReq.getDocname());
		verReq.setEnrollmentNumber(stuVerReq.getEnrollno());
		verReq.setFirstName(stuVerReq.getFirstname());
		verReq.setLastName(stuVerReq.getLastname());
		verReq.setMonthOfPassing(stuVerReq.getMonthofpassing());
		verReq.setYearOfPassingId(String.valueOf(stuVerReq.getYearofpassid()));
		verReq.setUploadDocumentPath(stuVerReq.getUploaddocpath());
		if(stuVerReq.getAltemail() != null) {
			verReq.setAltEmail(stuVerReq.getAltemail());
		}
		verReq.setRequestType(Long.valueOf(stuVerReq.getRequesttype()));
		verReq.setSemId(stuVerReq.getSemid());
		verReq.setStreamId(stuVerReq.getStreamid());
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    Date date = new Date();  
	    String currentDate = formatter.format(date); 
		
		verReq.setEditReason(verReq.getEditReason()+"#Student%"+currentDate+"%Request has been editted");
		
		//Added by mayuri
		verReq.setAltEmail(stuVerReq.getAltemail());
		Long embassyId = stuVerReq.getEmbassyid(); // this is  Long
		verReq.setEmbassyid(String.valueOf(embassyId));
		verReq.setEmbassyname(stuVerReq.getEmbassyname());
		verReq.setEmbassyadress(stuVerReq.getEmbassyaddress());
		
		
		verificationReqRepo.save(verReq);
		
		return true;
	}

	
	public boolean checkGStExemption(Long userid)
	{
		
		boolean flag=false;
		UserMasterEntity entities= userRepository.findById(userid).get();
		System.out.print("-----------Role Id----------- " + entities.getRoleId());
		System.out.print("-----------Gove Body---------" + entities.getAreYouGov());
		System.out.print("-----------GST Exemption---------" + entities.getGstExemption());
		long val = 2;
		if (entities.getRoleId().equals(val)) {
			
			if(entities.getAreYouGov() != null && !entities.getAreYouGov().trim().isEmpty() && entities.getGstExemption() != null && !entities.getGstExemption().trim().isEmpty() ) 
			{
			if (entities.getAreYouGov().equals("Y") && entities.getGstExemption().equals("Y")) {
		
				flag=true;
			}
			}
		}
		
		return flag;
	}
	
}
