package io.msa.moviecatalogservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.msa.moviecatalogservice.models.Catalogitem;
import io.msa.moviecatalogservice.models.Movie;
import io.msa.moviecatalogservice.models.Rating;
@Service
public class MovieInfo {


		@Autowired
		private RestTemplate restTemplate;

		@HystrixCommand(
				fallbackMethod = "getFallbackCatalogItem",
				threadPoolKey ="movieInfoPool",
				threadPoolProperties= {
						@HystrixProperty(name="coreSize", value="20"),
						@HystrixProperty(name="maxQueueSize", value="10")
				}
				)
		public Catalogitem getCatalogItem(Rating rating) {
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
			//Put them all together
			
			return new Catalogitem(movie.getName(), movie.getDescription(), rating.getRating());
		}
		public Catalogitem getFallbackCatalogItem(Rating rating) {
			return new Catalogitem("Movie name not found", "", rating.getRating());
		}
		
}
