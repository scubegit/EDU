package com.scube.edu.util;

import org.springframework.scheduling.annotation.Scheduled;

public class TaskScheduler {
	
	@Scheduled(cron="${cron.expression}"
			)
	public void doSomething() {
	    // Something
	}
	
	
	
}
