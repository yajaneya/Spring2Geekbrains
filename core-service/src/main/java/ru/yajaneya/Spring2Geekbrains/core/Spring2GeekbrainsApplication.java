package ru.yajaneya.Spring2Geekbrains.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("secret.properties")
public class Spring2GeekbrainsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring2GeekbrainsApplication.class, args);
	}

}
