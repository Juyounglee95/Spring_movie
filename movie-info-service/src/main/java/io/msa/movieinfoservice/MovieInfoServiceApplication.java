package io.msa.movieinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
@EnableEurekaClient
public class MovieInfoServiceApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(MovieInfoServiceApplication.class, args);
	}
	
	@org.springframework.context.annotation.Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
