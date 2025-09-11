package com.scube.edu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scube.edu.request.StatusChangeRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.StudentVerificationDocsResponse;
import com.scube.edu.response.UniversityVerifierResponse;
import com.scube.edu.response.VerificationResponse;
import com.scube.edu.service.UniversityApproverService;
import com.scube.edu.service.UniversityVerifierService;
import com.scube.edu.util.StringsUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/universityApprover")
public class UniversityApproverController {

	private static final Logger logger = LoggerFactory.getLogger(UniversityApproverController.class);
	/*
	 * BaseResponse response = null;
	 * 
	 * @Autowired UniversityApproverService universityApproverService;
	 * 
	 * 
	 * @GetMapping(value="/getUniversityApproverRequestList") public
	 * ResponseEntity<Object> getApproverRequestList() {
	 * 
	 * response = new BaseResponse();
	 * 
	 * try { List<UniversityVerifierResponse> list =
	 * universityApproverService.getUniversityApproverRequestList();
	 * response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
	 * response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
	 * response.setRespData(list);
	 * 
	 * return ResponseEntity.ok(response);
	 * 
	 * }catch (Exception e) {
	 * 
	 * logger.error(e.getMessage()); //BAD creds message comes from here
	 * 
	 * response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
	 * response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
	 * response.setRespData(e.getMessage());
	 * 
	 * return ResponseEntity.badRequest().body(response);
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * @PostMapping("/setStatusForUniversityDocument") public ResponseEntity<Object>
	 * setStatusForUniversityDocument(@RequestBody StatusChangeRequest
	 * statusChangeRequest) {
	 * 
	 * response = new BaseResponse(); System.out.
	 * println("*****VerifierController setStatusForVerifierDocument*****"
	 * +statusChangeRequest.getRemark()); try {
	 * 
	 * List<StudentVerificationDocsResponse> list =
	 * universityApproverService.setStatusForUniversityDocument(statusChangeRequest)
	 * ;
	 * 
	 * response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
	 * response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
	 * response.setRespData(null);
	 * 
	 * return ResponseEntity.ok(response);
	 * 
	 * }catch (Exception e) {
	 * 
	 * logger.error(e.getMessage()); //BAD creds message comes from here
	 * 
	 * response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
	 * response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
	 * response.setRespData(e.getMessage());
	 * 
	 * return ResponseEntity.badRequest().body(response);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * @PostMapping("/addNewRemark") public ResponseEntity<Object>
	 * addNewRemark(@RequestBody StatusChangeRequest statusChangeRequest) {
	 * 
	 * response = new BaseResponse(); System.out.
	 * println("*****VerifierController setStatusForVerifierDocument*****"
	 * +statusChangeRequest.getNewremark()); try {
	 * 
	 * boolean list = universityApproverService.addNewRemark(statusChangeRequest);
	 * 
	 * response.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
	 * response.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
	 * response.setRespData(null);
	 * 
	 * return ResponseEntity.ok(response);
	 * 
	 * }catch (Exception e) {
	 * 
	 * logger.error(e.getMessage()); //BAD creds message comes from here
	 * 
	 * response.setRespCode(StringsUtils.Response.FAILURE_RESP_CODE);
	 * response.setRespMessage(StringsUtils.Response.FAILURE_RESP_MSG);
	 * response.setRespData(e.getMessage());
	 * 
	 * return ResponseEntity.badRequest().body(response);
	 * 
	 * }
	 * 
	 * }
	 */
	
}
