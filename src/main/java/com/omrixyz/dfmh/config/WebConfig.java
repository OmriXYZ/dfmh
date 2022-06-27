package com.omrixyz.dfmh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
    private ApplicationContext applicationContext;
	
	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/", "classpath:/resources/donations/"  };
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry
        .addResourceHandler("/**")
        .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
    
//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//
//        var templateResolver = new SpringResourceTemplateResolver();
//
//        templateResolver.setApplicationContext(applicationContext);
//        templateResolver.setPrefix("/templates/");
//        templateResolver.setSuffix(".html");
//
//        return templateResolver;
//    }
}