package com.dmt.stockpicker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("org.springframework.web.client.RestTemplate")
public class StockpickerApplicationTests {

	@Test
	public void contextLoads() {
	}

}
