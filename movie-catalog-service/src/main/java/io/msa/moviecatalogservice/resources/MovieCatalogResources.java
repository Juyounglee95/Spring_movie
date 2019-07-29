package io.msa.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.msa.moviecatalogservice.models.Catalogitem;
import io.msa.moviecatalogservice.models.Movie;
import io.msa.moviecatalogservice.models.Rating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {
	
	@RequestMapping("/{userId}")
	public List<Catalogitem> getCatalog (@PathVariable("userId") String userId){
		
		RestTemplate restTemplate = new RestTemplate();
		//restTemplate.getForObject("http://localhost:8081/movies/foo", Movie.class);
		
		//get all rated movie IDs
		List<Rating> ratings = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678", 3)
		);
		
		return ratings.stream().map(rating->{
			
			Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
			
			return new Catalogitem(movie.getName(), "Description", rating.getRating());
		})
		.collect(Collectors.toList());
		//for each movie ID, call movie info service and get details
		
		//Put them all together
		
	}
}
