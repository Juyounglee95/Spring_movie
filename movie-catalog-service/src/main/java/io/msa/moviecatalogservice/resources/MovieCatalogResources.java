package io.msa.moviecatalogservice.resources;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.msa.moviecatalogservice.models.Catalogitem;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResources {
	
	@RequestMapping("/{userId}")
	public List<Catalogitem> getCatalog (@PathVariable("userId") String userId){
		return Collections.singletonList(
					new Catalogitem("Transformers", "Test", 4)
				);
	}
}
