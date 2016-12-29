package com.bufkes.service.recipe.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.service.RecipeService;
import static com.bufkes.service.recipe.util.Assert.isTrue;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	static final Logger logger = LogManager.getLogger(RecipeController.class.getName());

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
		isTrue(recipe != null, ErrorType.BAD_REQUEST, "No details supplied for updating the recipe");
		isTrue(recipeId.equals(recipe.getId()), ErrorType.DATA_VALIDATION, "Recipe Ids do not match");

		return recipeService.updateRecipe(recipe);
	}

	@RequestMapping(value = "{recipeId}", method = RequestMethod.DELETE)
	public void deleteRecipe(@PathVariable String recipeId) {
		recipeService.deleteRecipe(recipeId);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Recipe> getRecipesByName(@RequestParam String name) {
		isTrue(StringUtils.isNotBlank(name), ErrorType.BAD_REQUEST, "No name provided to search by");
		
		return recipeService.getRecipesByNameLike(name);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<Recipe> getAllRecipes() {
		return recipeService.getAllRecipes();
	}
}
