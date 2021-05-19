package com.scube.edu.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.scube.edu.model.YearlyVerReqBackup;
import com.scube.edu.repository.SaveYearDataRepository;

@Service
public class SavePrevYearData {
	
	@Autowired
	SaveYearDataRepository saveYearDataRepository;
	
	@Scheduled(cron = "0 0 12 1 * *")
	public int insertIntoTable() throws Exception {
		
		
		List<YearlyVerReqBackup> data=saveYearDataRepository.findprevYearData();
		
		saveYearDataRepository.saveAll(data);
		
		return 1;
		
	}

}
