package com.scube.edu.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.scube.edu.model.YearlyVerReqBackup;
import com.scube.edu.repository.YearlyVerReqBackupRepository;
import com.sun.media.jfxmedia.logging.Logger;

@Service
public class YearlyVerReqBackupScheduler {
	
	
	@Autowired
	YearlyVerReqBackupRepository yearlyVerReqBackupRepository;
	

	
	@Scheduled(cron = "${yearlybackup.cronTime}")
	public int insertIntoTable() throws Exception {
		
		System.out.println("-----------Running scheduler of Yearly backup Data---------- ");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -2); // to get previous year add -1
		
		Date prevdate = cal.getTime();
		int prevyear=prevdate.getYear();
		prevyear=1900+prevyear;
		List<YearlyVerReqBackup> datalist=null;
		String date=yearlyVerReqBackupRepository.getHighestdate();

		if(date!=null) {
			if(!date.equals(Integer.toString(prevyear)))
			datalist=yearlyVerReqBackupRepository.findprevYearDatabydate(date,Integer.toString(prevyear));
		}
		else {
			
			datalist=yearlyVerReqBackupRepository.findprevYearData();
		}
		
		if(datalist!=null)
		for(YearlyVerReqBackup datasave:datalist) {
			
			YearlyVerReqBackup data=new YearlyVerReqBackup();
			
		data.setFinancialYear(datasave.getFinancialYear());
		data.setTotalAmt(datasave.getTotalAmt());
		data.setNewReq(datasave.getNewReq());
		data.setClosedReq(datasave.getClosedReq());
		data.setPositiveReq(datasave.getPositiveReq());
		data.setNegativeReq(datasave.getNegativeReq());
		data.setDisputeRaised(datasave.getDisputeRaised());
		data.setDisputeClear(datasave.getDisputeClear());
				/*
				 * if(datasave.getCompanyNm()!=null) {
				 * data.setCompanyNm(datasave.getCompanyNm()); } else {
				 * data.setCompanyNm("Student");
				 * 
				 * } data.setGstno(datasave.getGstno());
				 * data.setUniversityAmt(datasave.getUniversityAmt());
				 * data.setSecureAmt(datasave.getSecureAmt());
				 */
		yearlyVerReqBackupRepository.save(data);
			
		}
		System.out.println("-----Bauckup VR data yearly sucessfully ------");
	
		return 1;
		
	}

}
