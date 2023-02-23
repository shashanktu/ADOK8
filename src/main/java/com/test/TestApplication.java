package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
	
	@RequestMapping("/")
	public String message()
	{
		return "This is a sample Containerized Single page web application Deployed to one of the Pod in AWS EKS cluster.";
	}

}
