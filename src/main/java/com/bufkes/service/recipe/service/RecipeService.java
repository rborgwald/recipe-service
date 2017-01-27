package com.bufkes.service.recipe.service;

import java.util.List;

import com.bufkes.service.recipe.model.Recipe;

public interface RecipeService {

	public Recipe saveRecipe(Recipe recipe);

	public Recipe getRecipeById(String id);

	public Recipe updateRecipe(Recipe recipe);

	public void deleteRecipe(String recipeId);

	public List<Recipe> getRecipesByNameLike(String name);
	
	public List<Recipe> getRecipesByMealType(String mealType);
	
	public List<Recipe> getRecipesByCuisineType(String mealType);

	public List<Recipe> getRecipesByProteinType(String proteinType);
	
	public List<Recipe> getRecipesByPreparationType(String preparationType);
	
	public List<Recipe> getAllRecipes();

}
