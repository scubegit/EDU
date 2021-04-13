package com.scube.edu.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scube.edu.model.DocumentMaster;
import com.scube.edu.model.PassingYearMaster;
import com.scube.edu.model.UserMasterEntity;
import com.scube.edu.repository.CollegeRepository;
import com.scube.edu.repository.DocumentRepository;
import com.scube.edu.request.DocumentAddRequest;
import com.scube.edu.request.UserAddRequest;
import com.scube.edu.response.BaseResponse;
import com.scube.edu.response.DocumentResponse;
import com.scube.edu.util.StringsUtils;


@Service
public class DocumentServiceImpl implements DocumentService{
	
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);
	
	@Autowired
	DocumentRepository documentRespository;
	BaseResponse  baseResponse = null;
    Base64.Decoder decoder = Base64.getDecoder();  

	@Override
	public List<DocumentResponse> getDocumentList(HttpServletRequest request) {
		
		logger.info("-----------getDocumentList---------------------");
		
		List<DocumentResponse> docList = new ArrayList<>();
		
		List<DocumentMaster> docEntities    = documentRespository.findAll();
		
		for(DocumentMaster entity : docEntities) {
			
			DocumentResponse documentResponse = new DocumentResponse();

			documentResponse.setId(entity.getId());
			documentResponse.setDocumentName(entity.getDocumentName());
			documentResponse.setUniversityId(entity.getUniversityId());
			
			docList.add(documentResponse);
		}
		return docList;
	}

	@Override
	public DocumentMaster getNameById(String documentName) {
		
		Long ID = Long.parseLong(documentName);
		System.out.println(ID);
		
		Optional<DocumentMaster> docMas = documentRespository.findById(ID);
		DocumentMaster resp = docMas.get();
		
		return resp;
	}
	
	
	//Abhishek Added
	
	@Override
	public Boolean addDocument(DocumentAddRequest documentRequest) throws Exception {
		
		
		
		DocumentMaster docMasterEntity  = new  DocumentMaster();
		
		docMasterEntity.setUniversityId(documentRequest.getUniversityId());//1
		docMasterEntity.setDocumentName(documentRequest.getDocumentName());//Name Document
		docMasterEntity.setCreateby(documentRequest.getCreated_by()); // Logged User Id 
		docMasterEntity.setIsdeleted(documentRequest.getIs_deleted()); // By Default N	
	
	     documentRespository.save(docMasterEntity);
	
		
			
		return true;
		
	}
	
	
	
	
	@Override
	public BaseResponse UpdateDocument(DocumentMaster documentMaster) throws Exception {
		
		baseResponse	= new BaseResponse();	

		Optional<DocumentMaster> docEntities  = documentRespository.findById(documentMaster.getId());
		
		   if(docEntities == null) {
			   
				throw new Exception(" Invalid ID");
			}
		
		
		DocumentMaster docEntit = docEntities.get();
		
		docEntit.setDocumentName(documentMaster.getDocumentName());
		
		
		 documentRespository.save(docEntit);
		
		   
	
		baseResponse.setRespCode(StringsUtils.Response.SUCCESS_RESP_CODE);
		baseResponse.setRespMessage(StringsUtils.Response.SUCCESS_RESP_MSG);
		baseResponse.setRespData("success");
		 
		return baseResponse;
	}
	
	
	//Abhishek Added
}
