package com.omrixyz.dfmh;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.omrixyz.dfmh.storage.StorageProperties;
import com.omrixyz.dfmh.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class DfmhApplication {

	public static void main(String[] args) {
		
		SpringApplication application = new SpringApplication(DfmhApplication.class);
		application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}

}
