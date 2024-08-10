package com.fininco.finincoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FinincoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinincoServerApplication.class, args);
	}

}
