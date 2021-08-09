package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations={"classpath:kaptchaConfig.xml"})
public class CcbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcbsApplication.class, args);
	}

}
