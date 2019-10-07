package com.dmt.stockpicker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.springframework.web.client.RestTemplate")
public class StockpickerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockpickerApplication.class, args);
	}

}