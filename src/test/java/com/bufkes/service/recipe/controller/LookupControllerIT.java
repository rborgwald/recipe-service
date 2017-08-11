package com.bufkes.service.recipe.controller;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.bufkes.service.recipe.RecipeApplicationITConfig;
import com.bufkes.service.recipe.model.CuisineType;
import com.bufkes.service.recipe.model.MealType;
import com.bufkes.service.recipe.model.PreparationType;
import com.bufkes.service.recipe.model.ProteinType;
import com.bufkes.service.recipe.repository.CuisineTypeRepository;
import com.bufkes.service.recipe.repository.MealTypeRepository;
import com.bufkes.service.recipe.repository.PreparationTypeRepository;
import com.bufkes.service.recipe.repository.ProteinTypeRepository;
import com.bufkes.service.recipe.util.TestDataBuilder;

public class LookupControllerIT extends RecipeApplicationITConfig {

	@Autowired
	private TestRestTemplate testRestTemplate;
		
	private HttpHeaders httpHeaders;
	
	@Autowired
	private MealTypeRepository mealTypeRepository;
	
	@Autowired
	private CuisineTypeRepository cuisineTypeRepository;
	
	@Autowired
	private ProteinTypeRepository proteinTypeRepository;
	
	@Autowired
	private PreparationTypeRepository preparationTypeRepository;
	
	@Before
    public void setUpData() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}
	
	@After
	public void tearDown() {
		mealTypeRepository.deleteAll();
		cuisineTypeRepository.deleteAll();
		proteinTypeRepository.deleteAll();
		preparationTypeRepository.deleteAll();
	}
	
	@Test
	public void testGetMealTypes() {
		MealType mealType = TestDataBuilder.buildMealType();
		mealTypeRepository.save(mealType);
		
		List<String> listOfTypes = testRestTemplate.getForObject("/api/lookup/mealtypes", List.class);

		assertEquals(1, listOfTypes.size());
	}
	
	@Test
	public void testGetMealTypes_noTypes() {
		List<String> listOfTypes = testRestTemplate.getForObject("/api/lookup/mealtypes", List.class);

		assertEquals(0, listOfTypes.size());
	}
	
	@Test
	public void testGetCuisineTypes() {
		CuisineType cuisineType = TestDataBuilder.buildCuisineType();
		cuisineTypeRepository.save(cuisineType);
		
		List<String> listOfTypes = testRestTemplate.getForObject("/api/lookup/cuisinetypes", List.class);

		assertEquals(1, listOfTypes.size());
	}
	
	@Test
	public void testGetCuisineTypes_noTypes() {
		List<String> listOfTypes = testRestTemplate.getForObject("/api/lookup/cuisinetypes", List.class);

		assertEquals(0, listOfTypes.size());
	}
	
	@Test
	public void testGetProteinTypes() {
		ProteinType proteinType = TestDataBuilder.buildProteinType();
		proteinTypeRepository.save(proteinType);
		
		List<String> listOfTypes = testRestTemplate.getForObject("/api/lookup/proteintypes", List.class);

		assertEquals(1, listOfTypes.size());
	}
	
	@Test
	public void testGetProteinTypes_noTypes() {
		List<String> listOfTypes = testRestTemplate.getForObject("/api/lookup/proteintypes", List.class);

		assertEquals(0, listOfTypes.size());
	}
	
	@Test
	public void testGetPreparationTypes() {
		PreparationType preparationType = TestDataBuilder.buildPreparationType();
		preparationTypeRepository.save(preparationType);
		
		List<String> listOfTypes = testRestTemplate.getForObject("/api/lookup/preparationtypes", List.class);

		assertEquals(1, listOfTypes.size());
	}
	
	@Test
	public void testGetPreparationTypes_noTypes() {
		List<String> listOfTypes = testRestTemplate.getForObject("/api/lookup/preparationtypes", List.class);

		assertEquals(0, listOfTypes.size());
	}
}
