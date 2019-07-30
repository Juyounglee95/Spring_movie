package io.msa.moviecatalogservice.rservice;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.msa.moviecatalogservice.models.Rating;
import io.msa.moviecatalogservice.models.UserRating;
@Service
public class UserRatingInfo {
	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserRating",
			commandProperties = {
					@HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
					@HystrixProperty(name ="circuitBreaker.requestVolumeThreshold", value = "5"),
					@HystrixProperty(name ="circuitBreaker.errorThresholdPercentage", value = "50"),
					@HystrixProperty(name ="circuitBreaker.sleepWindowInMilliseconds", value = "5000")
			}
			
			
			)
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		return restTemplate.getForObject("http://rating-date-service/ratingsdata/user/" +userId, UserRating.class);
	}
	
	public UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
		UserRating rating = new UserRating();
		rating.setUserId(userId);
		rating.setRatings(Arrays.asList( 
				new Rating("0",0)));
		return rating;
	}
}
