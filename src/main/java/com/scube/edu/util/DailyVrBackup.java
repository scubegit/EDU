package com.scube.edu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.scube.edu.model.DailyVrBackupEntity;
import com.scube.edu.repository.DailyVrBackupRepository;

@Service
public class DailyVrBackup {

	@Autowired
	DailyVrBackupRepository dailyVrBackupRepository;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public int insertDailyDataIntoTable() throws Exception {
		
		System.out.println("-----------Running scheduler of Daily backup Data---------- ");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -2); // to get previous year add -1
		
		Date date = cal.getTime();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date2=simpleDateFormat.format(date);
		
		
		String date1=dailyVrBackupRepository.getHighestdate();	
	 
	  System.out.println("date"+date);
	  System.out.println("date1="+date1);
	  System.out.println("date2="+date2);
	  List<DailyVrBackupEntity> datalist=null;
	 /* if(date1==date2) {
		  datalist=dailyVrBackupRepository.findDailyData();
	  }
	  else {*/
		  if(date1==null){
			  datalist=dailyVrBackupRepository.getDailyData();
		  }
		  else {
		  datalist=dailyVrBackupRepository.getDailyDataSchedulerSkipped(date1);
		  }
	  //}
		if(datalist!=null)
		for(DailyVrBackupEntity datasave:datalist) {
			
			DailyVrBackupEntity data=new DailyVrBackupEntity();
	
		if(datasave.getCompanyNm()!=null) {
		data.setCompanyNm(datasave.getCompanyNm());
		}
		else
		{
			data.setCompanyNm("STUDENT");

		}
		data.setReqDate(datasave.getReqDate());
		data.setTotalAmt(datasave.getTotalAmt());
		data.setGstno(datasave.getGstno());
		data.setUniversityAmt(datasave.getUniversityAmt());
		data.setSecureAmt(datasave.getSecureAmt());
		dailyVrBackupRepository.save(data);
			
		}
		System.out.println("-----Bauckup VR data Daily sucessfully ------");
	
		return 1;
		
	}

}
