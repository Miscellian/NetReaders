package com.netreaders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.netreaders"})
@EnableScheduling
public class NetReadersApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetReadersApplication.class, args);
	}

}
