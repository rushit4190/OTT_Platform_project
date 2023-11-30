package com.NextLeap.OTT_platform_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// to bypass spring security, add exclude
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class OttPlatformProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(OttPlatformProjectApplication.class, args);
	}

}
