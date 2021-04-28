package com.scube.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.scube.edu.model.FileStorageProperties;

import javafx.application.Application;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})




public class EduApplication extends SpringBootServletInitializer  {

	
	/*
	 * @Override protected SpringApplicationBuilder
	 * configure(SpringApplicationBuilder application) { return
	 * application.sources(Application.class); }
	 */
	public static void main(String[] args) {
		SpringApplication.run(EduApplication.class, args);
	}

	
	
}
