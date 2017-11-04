package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.UserController;

@RestController
@SpringBootApplication
@ComponentScan(basePackageClasses = UserController.class)
public class UserApplication {

	@RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String home() {
		return "Welcome Page";
	}

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
