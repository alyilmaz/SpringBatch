package com.ali.batch.app;

import com.ali.batch.config.JPAConfiguration;
import com.ali.batch.config.ResourceServerConfig;
import com.ali.batch.config.SpringBatchConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan("com.ali")
@Import({JPAConfiguration.class, ResourceServerConfig.class, SpringBatchConfig.class})
@EnableBatchProcessing
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


}
