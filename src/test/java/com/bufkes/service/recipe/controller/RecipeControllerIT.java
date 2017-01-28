package com.bufkes.service.recipe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.bufkes.service.recipe.RecipeApplicationITConfig;
import com.bufkes.service.recipe.exception.ServiceError;
import com.bufkes.service.recipe.model.CuisineType;
import com.bufkes.service.recipe.model.MealType;
import com.bufkes.service.recipe.model.PreparationType;
import com.bufkes.service.recipe.model.ProteinType;
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.repository.CuisineTypeRepository;
import com.bufkes.service.recipe.repository.MealTypeRepository;
import com.bufkes.service.recipe.repository.PreparationTypeRepository;
import com.bufkes.service.recipe.repository.ProteinTypeRepository;
import com.bufkes.service.recipe.repository.RecipeRepository;
import com.bufkes.service.recipe.util.TestDataBuilder;
import com.bufkes.service.recipe.util.TestUtil;

public class RecipeControllerIT extends RecipeApplicationITConfig {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private MealTypeRepository mealTypeRepository;
	
	@Autowired
	private CuisineTypeRepository cuisineTypeRepository;
	
	@Autowired
	private ProteinTypeRepository proteinTypeRepository;
	
	@Autowired
	private PreparationTypeRepository preparationTypeRepository;
	
	private HttpHeaders httpHeaders;
	
	@Before
    public void setUpData() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	}
	
	@After
	public void tearDown() {
		recipeRepository.deleteAll();
		mealTypeRepository.deleteAll();
		cuisineTypeRepository.deleteAll();
	}
	
	@Test
	public void testCreateReadUpdateDeleteRecipe() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		Recipe blankRecipe = testRestTemplate.getForObject("/api/recipes/{recipeId}", Recipe.class, recipe.getId());
		
		// making sure recipe does not exist
		assertThat(blankRecipe).isNull();
		
		// create recipe
		ResponseEntity<Recipe> createRecipeResponse = testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe), this.httpHeaders), Recipe.class);
		assertEquals(HttpStatus.OK, createRecipeResponse.getStatusCode());
		// get id from created recipe
		recipe.setId(createRecipeResponse.getBody().getId());
		
		// verifying if the recipe got saved
		Recipe newRecipe = testRestTemplate.getForObject("/api/recipes/{recipeId}", Recipe.class, recipe.getId());
		
		assertEquals(recipe.getId(), newRecipe.getId());
		assertEquals(recipe.getName(), newRecipe.getName());
		assertEquals(recipe.getSource(), newRecipe.getSource());
		assertEquals(recipe.getVolume(), newRecipe.getVolume());
		assertEquals(recipe.getPage(), newRecipe.getPage());
		
		// updating the recipe
		String updatedName = "someOtherName";
		recipe.setName(updatedName);
		String updatedVolume = "someOtherVolume";
		recipe.setVolume(updatedVolume);
		
		ResponseEntity<Recipe> updateRecipeResponse = testRestTemplate.exchange("/api/recipes/{recipeId}", HttpMethod.PUT, new HttpEntity<>(recipe), Recipe.class, recipe.getId());
		assertEquals(HttpStatus.OK, updateRecipeResponse.getStatusCode());
		
		// verify updates were saved
		Recipe updatedRecipe = testRestTemplate.getForObject("/api/recipes/{recipeId}", Recipe.class, recipe.getId());
		
		assertEquals(recipe.getId(), updatedRecipe.getId());
		assertEquals(updatedName, updatedRecipe.getName());
		assertEquals(recipe.getSource(), updatedRecipe.getSource());
		assertEquals(updatedVolume, updatedRecipe.getVolume());
		assertEquals(recipe.getPage(), updatedRecipe.getPage());
		
		// deleting recipe
		testRestTemplate.delete("/api/recipes/{recipeId}", recipe.getId());
		
		// verify recipe was deleted
		Recipe deletedRecipe = testRestTemplate.getForObject("/api/recipes/{recipeId}", Recipe.class, recipe.getId());
		assertThat(deletedRecipe).isNull();
	}
	
	@Test
	public void testUpdateRecipe_RecipeNotFound() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		ResponseEntity<ServiceError> updateRecipeResponse = testRestTemplate.exchange("/api/recipes/{recipeId}", HttpMethod.PUT, new HttpEntity<>(recipe), ServiceError.class, recipe.getId());
		assertEquals(HttpStatus.BAD_REQUEST, updateRecipeResponse.getStatusCode());
		assertThat(updateRecipeResponse.getBody().getErrorMessage()).contains("Recipe not found");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_SameNames() {
		Recipe recipe1 = TestDataBuilder.buildRecipe();
		Recipe recipe2 = TestDataBuilder.buildRecipe();
		Recipe recipe3 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe3), this.httpHeaders), Recipe.class);
	
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/name/" + recipe1.getName(), List.class);
		assertEquals(3, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_TwoNamesMatch() {
		Recipe recipe1 = TestDataBuilder.buildRecipe();
		Recipe recipe2 = TestDataBuilder.buildRecipe();
		Recipe recipe3 = TestDataBuilder.buildRecipe();
		recipe3.setName("foo");
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe3), this.httpHeaders), Recipe.class);
	
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/name/" + recipe1.getName(), List.class);
		assertEquals(2, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_OneNameMatches() {
		Recipe recipe1 = TestDataBuilder.buildRecipe();
		Recipe recipe2 = TestDataBuilder.buildRecipe();
		Recipe recipe3 = TestDataBuilder.buildRecipe();
		recipe2.setName("123");
		recipe3.setName("foo");
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe3), this.httpHeaders), Recipe.class);
	
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/name/" + recipe1.getName(), List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_PartialMatchBeginning() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		recipe.setName("foobar");
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe), this.httpHeaders), Recipe.class);

		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/name/foo", List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_PartialMatchEnd() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		recipe.setName("foobar");
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe), this.httpHeaders), Recipe.class);

		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/name/bar", List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_PartialMatchMiddle() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		recipe.setName("foobar");
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe), this.httpHeaders), Recipe.class);

		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/name/oba", List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_NoMatch() {
		Recipe recipe1 = TestDataBuilder.buildRecipe();
		Recipe recipe2 = TestDataBuilder.buildRecipe();
		Recipe recipe3 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe3), this.httpHeaders), Recipe.class);
	
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/name/foobar", List.class);
		assertEquals(0, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_IgnoreCase() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		recipe.setName("foobar");
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe), this.httpHeaders), Recipe.class);
	
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/name/oBA", List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByMealType_oneMatch() {
		// insert one with matching meal type, other without
		MealType mealType = TestDataBuilder.buildMealType();
		mealTypeRepository.save(mealType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setMealType(mealType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/mealtype/" + mealType.getName(), List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByMealType_multipleMatches() {
		// insert two with matching meal type, other without
		MealType mealType = TestDataBuilder.buildMealType();
		mealTypeRepository.save(mealType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setMealType(mealType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		r2.setMealType(mealType);
		Recipe r3 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r3), this.httpHeaders), Recipe.class);

		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/mealtype/" + mealType.getName(), List.class);
		assertEquals(2, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByMealType_noMatch() {
		// insert none with matching meal type
		Recipe r1 = TestDataBuilder.buildRecipe();
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
				
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/mealtype/foo", List.class);
		assertEquals(0, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByMealType_ignoreCase() {
		// insert one with matching meal type, other without
		MealType mealType = TestDataBuilder.buildMealType();
		mealTypeRepository.save(mealType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setMealType(mealType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/mealtype/" + StringUtils.lowerCase(mealType.getName()), List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByCuisineType_oneMatch() {
		CuisineType cuisineType = TestDataBuilder.buildCuisineType();
		cuisineTypeRepository.save(cuisineType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setCuisineType(cuisineType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/cuisinetype/" + cuisineType.getName(), List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByCuisineType_multipleMatches() {
		// insert two with matching meal type, other without
		CuisineType cuisineType = TestDataBuilder.buildCuisineType();
		cuisineTypeRepository.save(cuisineType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setCuisineType(cuisineType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		r2.setCuisineType(cuisineType);
		Recipe r3 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r3), this.httpHeaders), Recipe.class);

		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/cuisinetype/" + cuisineType.getName(), List.class);
		assertEquals(2, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByCuisineType_noMatch() {
		// insert none with matching meal type
		Recipe r1 = TestDataBuilder.buildRecipe();
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
				
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/cuisinetype/foo", List.class);
		assertEquals(0, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByCuisineType_ignoreCase() {
		// insert one with matching meal type, other without
		CuisineType cuisineType = TestDataBuilder.buildCuisineType();
		cuisineTypeRepository.save(cuisineType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setCuisineType(cuisineType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/cuisinetype/" + StringUtils.lowerCase(cuisineType.getName()), List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByProteinType_oneMatch() {
		ProteinType proteinType = TestDataBuilder.buildProteinType();
		proteinTypeRepository.save(proteinType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setProteinType(proteinType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/proteintype/" + proteinType.getName(), List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByProteinType_multipleMatches() {
		// insert two with matching meal type, other without
		ProteinType proteinType = TestDataBuilder.buildProteinType();
		proteinTypeRepository.save(proteinType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setProteinType(proteinType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		r2.setProteinType(proteinType);
		Recipe r3 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r3), this.httpHeaders), Recipe.class);

		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/proteintype/" + proteinType.getName(), List.class);
		assertEquals(2, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByProteinType_noMatch() {
		// insert none with matching meal type
		Recipe r1 = TestDataBuilder.buildRecipe();
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
				
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/proteintype/foo", List.class);
		assertEquals(0, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByProteinType_ignoreCase() {
		// insert one with matching meal type, other without
		ProteinType proteinType = TestDataBuilder.buildProteinType();
		proteinTypeRepository.save(proteinType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setProteinType(proteinType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/proteintype/" + StringUtils.lowerCase(proteinType.getName()), List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByPreparationType_oneMatch() {
		PreparationType preparationType = TestDataBuilder.buildPreparationType();
		preparationTypeRepository.save(preparationType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setPreparationType(preparationType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/preparationtype/" + preparationType.getName(), List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByPreparationType_multipleMatches() {
		// insert two with matching meal type, other without
		PreparationType preparationType = TestDataBuilder.buildPreparationType();
		preparationTypeRepository.save(preparationType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setPreparationType(preparationType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		r2.setPreparationType(preparationType);
		Recipe r3 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r3), this.httpHeaders), Recipe.class);

		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/preparationtype/" + preparationType.getName(), List.class);
		assertEquals(2, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByPreparationType_noMatch() {
		// insert none with matching meal type
		Recipe r1 = TestDataBuilder.buildRecipe();
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
				
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/preparationtype/foo", List.class);
		assertEquals(0, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByPreparationType_ignoreCase() {
		// insert one with matching meal type, other without
		PreparationType preparationType = TestDataBuilder.buildPreparationType();
		preparationTypeRepository.save(preparationType);
		
		Recipe r1 = TestDataBuilder.buildRecipe();
		r1.setPreparationType(preparationType);
		Recipe r2 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(r2), this.httpHeaders), Recipe.class);
		
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/preparationtype/" + StringUtils.lowerCase(preparationType.getName()), List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllRecipes() {
		Recipe recipe1 = TestDataBuilder.buildRecipe();
		Recipe recipe2 = TestDataBuilder.buildRecipe();
		Recipe recipe3 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/api/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe3), this.httpHeaders), Recipe.class);
	
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/all", List.class);
		assertEquals(3, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllRecipes_NoRecipes() {
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/api/recipes/all", List.class);
		assertEquals(0, listOfRecipes.size());
	}
}
