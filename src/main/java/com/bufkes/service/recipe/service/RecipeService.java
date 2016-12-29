package com.bufkes.service.recipe.service;

import com.bufkes.service.recipe.model.Recipe;

public interface RecipeService {

	public Recipe saveRecipe(Recipe recipe);

	public Recipe getRecipeById(String id);

	public Recipe updateRecipe(Recipe recipe);

	public void deleteRecipe(String recipeId);

}
