package io.msa.ratingdateservice.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.msa.ratingdateservice.model.Rating;

@RestController
@RequestMapping("/ratingdata")
public class RatingDataResources {
	@RequestMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating (movieId, 4);
	}
}
