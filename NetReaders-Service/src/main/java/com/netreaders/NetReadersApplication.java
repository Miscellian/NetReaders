package com.netreaders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.netreaders"})
public class NetReadersApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetReadersApplication.class, args);
	}

}
