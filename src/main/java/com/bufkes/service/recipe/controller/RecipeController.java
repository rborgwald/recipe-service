package com.bufkes.service.recipe.controller;

import static com.bufkes.service.recipe.util.Assert.isTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.model.SearchCriterion;
import com.bufkes.service.recipe.service.RecipeService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${gateway.api.prefix}/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	static final Logger LOG = LogManager.getLogger(RecipeController.class.getName());

	@RequestMapping(value = "{recipeId}", method = RequestMethod.GET)
	public Recipe readRecipe(@PathVariable String recipeId) {
		return recipeService.getRecipeById(recipeId);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public Recipe createRecipe(@RequestBody Recipe recipe) {
		return recipeService.saveRecipe(recipe);
	}

	@RequestMapping(value = "{recipeId}", method = RequestMethod.PUT)
	public Recipe updateRecipe(@PathVariable String recipeId, @RequestBody Recipe recipe) {
		LOG.info("Incoming recipe: " + recipe);
		isTrue(recipe != null, ErrorType.BAD_REQUEST, "No details supplied for updating the recipe");
		isTrue(recipeId.equals(recipe.getId()), ErrorType.DATA_VALIDATION, "Recipe Ids do not match");

		Recipe updatedRecipe = recipeService.updateRecipe(recipe);
		LOG.info("After updating: " + updatedRecipe);
		return updatedRecipe;
	}

	@RequestMapping(value = "{recipeId}", method = RequestMethod.DELETE)
	public void deleteRecipe(@PathVariable String recipeId) {
		recipeService.deleteRecipe(recipeId);
	}

	@RequestMapping(value = "{recipeId}/images", method = RequestMethod.PUT)
	public Recipe addImage(@PathVariable String recipeId, @RequestParam("file") MultipartFile file) {
		LOG.info("Adding image to recipe {}", recipeId);

		isTrue(StringUtils.isNotBlank(recipeId), ErrorType.BAD_REQUEST, "Recipe id cannot be blank");
		isTrue(!file.isEmpty(), ErrorType.BAD_REQUEST, "Unable to save image because image is blank");

		return recipeService.addImage(recipeId, file);
	}

	@RequestMapping(
			value = "/{recipeId}/images",
			method = RequestMethod.GET,
			produces = MediaType.IMAGE_JPEG_VALUE
	)
	public @ResponseBody byte[] getImage(@PathVariable String recipeId) throws IOException {
		LOG.info("Getting image for recipe {}", recipeId);

		isTrue(StringUtils.isNotBlank(recipeId), ErrorType.BAD_REQUEST, "Recipe id cannot be blank");

		return recipeService.getImage(recipeId);
	}

	@RequestMapping(value = "{recipeId}/images", method = RequestMethod.DELETE)
	public Recipe deleteImage(@PathVariable String recipeId) { return recipeService.deleteImage(recipeId); }

	@RequestMapping(value = "{recipeId}/searchcriteria", method = RequestMethod.PUT)
	public Recipe addSearchCriterion(@PathVariable String recipeId, @RequestBody Map<String, String> criteria) {
		for (Map.Entry<String, String> criterion : criteria.entrySet()) {
			isTrue(StringUtils.isNotBlank(criterion.getKey()), ErrorType.BAD_REQUEST, "No type specified for search category");
			recipeService.addSearchCriterion(recipeId, criterion.getKey(), criterion.getValue());
		}
		
		return recipeService.getRecipeById(recipeId);
	}
	
	@RequestMapping(value = "{recipeId}/searchcriteria", method = RequestMethod.GET)
	public Map<String, SearchCriterion> getSearchCriteria(@PathVariable String recipeId) {
		Recipe recipe = recipeService.getRecipeById(recipeId);
		isTrue(recipe != null, ErrorType.NO_DATA_FOUND, "Recipe not found");
		
		Map<String, SearchCriterion> searchCriteria = new HashMap<String, SearchCriterion>();
		
		if (recipe.getMealType() != null) {
			searchCriteria.put("mealtype", recipe.getMealType());
		}
		
		if (recipe.getCuisineType() != null) {
			searchCriteria.put("cuisinetype", recipe.getCuisineType());
		}
		
		if (recipe.getProteinType() != null) {
			searchCriteria.put("proteintype", recipe.getProteinType());
		}
		
		if (recipe.getPreparationType() != null) {
			searchCriteria.put("preparationtype", recipe.getPreparationType());
		}
		
		return searchCriteria;
	}
	
	@RequestMapping(method = RequestMethod.GET, params={})
	public List<Recipe> findRecipes(@RequestParam(value = "name", required = false, defaultValue="") String name, 
			@RequestParam(value = "mealtype", required = false, defaultValue="") String mealType,
			@RequestParam(value = "cuisinetype", required = false, defaultValue="") String cuisineType, 
			@RequestParam(value = "preparationtype", required = false, defaultValue="") String preparationType,
			@RequestParam(value = "proteintype", required = false, defaultValue="") String proteinType,
			@RequestParam(value = "stars", required = false, defaultValue = "") Integer stars,
			@RequestParam(value = "new", required = false, defaultValue = "") Boolean newRecipe) {
		
		return recipeService.findRecipes(name, mealType, cuisineType, preparationType, proteinType, stars, newRecipe);
	}
	
	@RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
	public List<Recipe> getRecipesByName(@PathVariable String name) {
		isTrue(StringUtils.isNotBlank(name), ErrorType.BAD_REQUEST, "No name provided to search by");
		
		return recipeService.getRecipesByNameLike(name);
	}
	
	@RequestMapping(value = "/mealtype/{mealType}", method = RequestMethod.GET)
	public List<Recipe> getReceipesByMealType(@PathVariable String mealType) {
		isTrue(StringUtils.isNotBlank(mealType), ErrorType.BAD_REQUEST, "No meal type provided to search by");
		
		return recipeService.getRecipesByMealType(mealType);
	}
	
	@RequestMapping(value = "/cuisinetype/{cuisineType}", method = RequestMethod.GET)
	public List<Recipe> getReceipesByCuisineType(@PathVariable String cuisineType) {
		isTrue(StringUtils.isNotBlank(cuisineType), ErrorType.BAD_REQUEST, "No cuisine type provided to search by");
		
		return recipeService.getRecipesByCuisineType(cuisineType);
	}
	
	@RequestMapping(value = "/proteintype/{proteinType}", method = RequestMethod.GET)
	public List<Recipe> getReceipesByProteinType(@PathVariable String proteinType) {
		isTrue(StringUtils.isNotBlank(proteinType), ErrorType.BAD_REQUEST, "No protein type provided to search by");
		
		return recipeService.getRecipesByProteinType(proteinType);
	}
	
	@RequestMapping(value = "/preparationtype/{preparationType}", method = RequestMethod.GET)
	public List<Recipe> getReceipesByPreparationType(@PathVariable String preparationType) {
		isTrue(StringUtils.isNotBlank(preparationType), ErrorType.BAD_REQUEST, "No preparation type provided to search by");
		
		return recipeService.getRecipesByPreparationType(preparationType);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Recipe> getAllRecipes() {
		List<Recipe> allRecipes = recipeService.getAllRecipes();
		return allRecipes;
	}
}
