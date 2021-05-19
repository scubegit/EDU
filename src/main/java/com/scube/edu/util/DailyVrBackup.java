package com.scube.edu.util;

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
		
		List<DailyVrBackupEntity> datalist=dailyVrBackupRepository.findDailyData();
		
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
