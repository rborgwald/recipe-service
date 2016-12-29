package com.bufkes.service.recipe.service.impl;
import static com.bufkes.service.recipe.util.Assert.isTrue;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.repository.RecipeRepository;
import com.bufkes.service.recipe.service.RecipeService;


@Component("recipeService")
public class RecipeServiceImpl implements RecipeService {

	private static final Logger logger = LogManager.getLogger(RecipeServiceImpl.class.getName());
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	public Recipe saveRecipe(Recipe recipe) {
		isTrue(recipe != null, ErrorType.SYSTEM, "Recipe is null or empty");
		return recipeRepository.save(recipe);
	}

	public Recipe getRecipeById(String id) {
		isTrue(!StringUtils.isEmpty(id), ErrorType.SYSTEM, "Recipe id is null or empty");
		Recipe recipe = recipeRepository.findById(id);
		
		if (recipe == null) {
			String errorMsg = "No recipe found for id: " + id;
			logger.error(errorMsg);
			return null;
		} else {
			return recipe;
		}
	}

	public Recipe updateRecipe(Recipe recipe) {
		isTrue(recipe != null, ErrorType.SYSTEM, "Recipe details not available");
		Recipe existingRecipe = recipeRepository.findById(recipe.getId());
		isTrue(existingRecipe != null, ErrorType.NO_DATA_FOUND, "Recipe not found");

		recipeRepository.save(recipe);
		
		return recipeRepository.findById(recipe.getId());
	}

	@Transactional
	public void deleteRecipe(String recipeId) {
		isTrue(!StringUtils.isEmpty(recipeId), ErrorType.SYSTEM, "Recipe id is null or empty");
		recipeRepository.deleteById(recipeId);
	}

}
