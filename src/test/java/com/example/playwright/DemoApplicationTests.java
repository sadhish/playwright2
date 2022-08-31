package com.example.playwright;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;

@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {
	@Test
	void contextLoads() {
		Assert.assertEquals(true,true);
	}
}