package io.msa.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sun.javadoc.ParameterizedType;

import io.msa.moviecatalogservice.models.Catalogitem;
import io.msa.moviecatalogservice.models.Movie;
import io.msa.moviecatalogservice.models.Rating;
import io.msa.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient; 
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@RequestMapping("/{userId}")
	@HystrixCommand(fallbackMethod = "getFallbackCatalog")
	public List<Catalogitem> getCatalog (@PathVariable("userId") String userId){
		
		WebClient.Builder builder = WebClient.builder();
		
		UserRating ratings =restTemplate.getForObject("http://rating-date-service/ratingsdata/user/" +userId, UserRating.class);
		
		return ratings.getRatings().stream().map(rating->{
			//for each movie ID, call movie info service and get details
			
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
			//Put them all together
			
			return new Catalogitem(movie.getName(), movie.getDescription(), rating.getRating());
		})
		.collect(Collectors.toList());
		
		
		
	}
	public List<Catalogitem> getFallbackCatalog(@PathVariable("userId") String userId){
		return Arrays.asList(new Catalogitem("No movie", "", 0));
	}
}
/*Movie movie = webClientBuilder.build()
.get()
.uri("http://localhost:8082/movies/"+rating.getMovieId())
.retrieve()
.bodyToMono(Movie.class)
.block();
*/