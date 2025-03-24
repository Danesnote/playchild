package com.pod.playchild;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// 아놀자 개발
// 2025.03.03 start.

@SpringBootApplication(scanBasePackages = {""})
public class PlaychildApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaychildApplication.class, args);
	}

}
