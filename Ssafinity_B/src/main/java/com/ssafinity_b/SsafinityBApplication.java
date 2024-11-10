package com.ssafinity_b;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SsafinityBApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsafinityBApplication.class, args);
	}

}
