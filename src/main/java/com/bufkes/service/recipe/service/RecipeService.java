package com.bufkes.service.recipe.service;

import java.io.IOException;
import java.util.List;

import com.bufkes.service.recipe.model.Recipe;
import org.springframework.web.multipart.MultipartFile;

public interface RecipeService {

	public Recipe saveRecipe(Recipe recipe);

	public Recipe getRecipeById(String id);

	public Recipe updateRecipe(Recipe recipe);

	public void deleteRecipe(String recipeId);
	
	public Recipe addSearchCriterion(String recipeId, String category, String type);

	public List<Recipe> getRecipesByNameLike(String name);
	
	public List<Recipe> getRecipesByMealType(String mealType);
	
	public List<Recipe> getRecipesByCuisineType(String mealType);

	public List<Recipe> getRecipesByProteinType(String proteinType);
	
	public List<Recipe> getRecipesByPreparationType(String preparationType);
	
	public List<Recipe> getAllRecipes();

	public List<Recipe> findRecipes(String name, String mealType, String cuisineType, String preparationType,
			String proteinType, Integer stars, Boolean triedIt);

	Recipe addImage(String recipeId, MultipartFile file);

	byte[] getImage(String recipeId) throws IOException;

	Recipe deleteImage(String recipeId);
}
