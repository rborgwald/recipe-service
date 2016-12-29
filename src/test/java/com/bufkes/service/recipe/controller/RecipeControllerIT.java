package com.bufkes.service.recipe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
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
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.repository.RecipeRepository;
import com.bufkes.service.recipe.util.TestDataBuilder;
import com.bufkes.service.recipe.util.TestUtil;

public class RecipeControllerIT extends RecipeApplicationITConfig {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
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
	}
	
	@Test
	public void testCreateReadUpdateDeleteRecipe() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		Recipe blankRecipe = testRestTemplate.getForObject("/recipes/{recipeId}", Recipe.class, recipe.getId());
		
		// making sure recipe does not exist
		assertThat(blankRecipe).isNull();
		
		// create recipe
		ResponseEntity<Recipe> createRecipeResponse = testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe), this.httpHeaders), Recipe.class);
		assertEquals(HttpStatus.OK, createRecipeResponse.getStatusCode());
		// get id from created recipe
		recipe.setId(createRecipeResponse.getBody().getId());
		
		// verifying if the recipe got saved
		Recipe newRecipe = testRestTemplate.getForObject("/recipes/{recipeId}", Recipe.class, recipe.getId());
		
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
		
		ResponseEntity<Recipe> updateRecipeResponse = testRestTemplate.exchange("/recipes/{recipeId}", HttpMethod.PUT, new HttpEntity<>(recipe), Recipe.class, recipe.getId());
		assertEquals(HttpStatus.OK, updateRecipeResponse.getStatusCode());
		
		// verify updates were saved
		Recipe updatedRecipe = testRestTemplate.getForObject("/recipes/{recipeId}", Recipe.class, recipe.getId());
		
		assertEquals(recipe.getId(), updatedRecipe.getId());
		assertEquals(updatedName, updatedRecipe.getName());
		assertEquals(recipe.getSource(), updatedRecipe.getSource());
		assertEquals(updatedVolume, updatedRecipe.getVolume());
		assertEquals(recipe.getPage(), updatedRecipe.getPage());
		
		// deleting recipe
		testRestTemplate.delete("/recipes/{recipeId}", recipe.getId());
		
		// verify recipe was deleted
		Recipe deletedRecipe = testRestTemplate.getForObject("/recipes/{recipeId}", Recipe.class, recipe.getId());
		assertThat(deletedRecipe).isNull();
	}
	
	@Test
	public void testUpdateRecipe_RecipeNotFound() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		ResponseEntity<ServiceError> updateRecipeResponse = testRestTemplate.exchange("/recipes/{recipeId}", HttpMethod.PUT, new HttpEntity<>(recipe), ServiceError.class, recipe.getId());
		assertEquals(HttpStatus.BAD_REQUEST, updateRecipeResponse.getStatusCode());
		assertThat(updateRecipeResponse.getBody().getErrorMessage()).contains("Recipe not found");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_SameNames() {
		Recipe recipe1 = TestDataBuilder.buildRecipe();
		Recipe recipe2 = TestDataBuilder.buildRecipe();
		Recipe recipe3 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe3), this.httpHeaders), Recipe.class);
	
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/recipes?name=" + recipe1.getName(), List.class);
		assertEquals(3, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_TwoNamesMatch() {
		Recipe recipe1 = TestDataBuilder.buildRecipe();
		Recipe recipe2 = TestDataBuilder.buildRecipe();
		Recipe recipe3 = TestDataBuilder.buildRecipe();
		recipe3.setName("foo");
		
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe3), this.httpHeaders), Recipe.class);
	
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/recipes?name=" + recipe1.getName(), List.class);
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
		
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe3), this.httpHeaders), Recipe.class);
	
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/recipes?name=" + recipe1.getName(), List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_PartialMatchBeginning() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		recipe.setName("foobar");
		
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe), this.httpHeaders), Recipe.class);

		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/recipes?name=foo", List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_PartialMatchEnd() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		recipe.setName("foobar");
		
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe), this.httpHeaders), Recipe.class);

		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/recipes?name=bar", List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_PartialMatchMiddle() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		recipe.setName("foobar");
		
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe), this.httpHeaders), Recipe.class);

		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/recipes?name=oba", List.class);
		assertEquals(1, listOfRecipes.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetRecipesByName_NoMatch() {
		Recipe recipe1 = TestDataBuilder.buildRecipe();
		Recipe recipe2 = TestDataBuilder.buildRecipe();
		Recipe recipe3 = TestDataBuilder.buildRecipe();
		
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe1), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe2), this.httpHeaders), Recipe.class);
		testRestTemplate.postForEntity("/recipes", new HttpEntity<>(TestUtil.asJsonString(recipe3), this.httpHeaders), Recipe.class);
	
		List<Recipe> listOfRecipes = testRestTemplate.getForObject("/recipes?name=foobar", List.class);
		assertEquals(0, listOfRecipes.size());
	}
}
