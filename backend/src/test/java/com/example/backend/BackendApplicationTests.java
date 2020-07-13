package com.example.backend;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest
@ContextConfiguration(initializers = {BackendApplicationTests.Initializer.class})
class BackendApplicationTests {

	public static MySQLContainer mysql = new MySQLContainer("mysql:5.6")
			.withDatabaseName("db_example")
			.withUsername("sa")
			.withPassword("sa");

	static class Initializer
			implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			mysql.start();
			TestPropertyValues.of(
					"spring.datasource.url=" + mysql.getJdbcUrl(),
					"spring.datasource.username=" + mysql.getUsername(),
					"spring.datasource.password=" + mysql.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@AfterClass
	public static void logout() {
		mysql.stop();
	}

	@Test
	void contextLoads() {
	}

}
