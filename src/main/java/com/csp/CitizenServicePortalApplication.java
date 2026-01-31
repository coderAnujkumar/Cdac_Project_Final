package com.csp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// this is main class spring run this class
@SpringBootApplication // this contain 3 annotation 1.configration 2. EnableAutoConfigration 3. ComponentScan
public class CitizenServicePortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitizenServicePortalApplication.class, args);
	}

}
