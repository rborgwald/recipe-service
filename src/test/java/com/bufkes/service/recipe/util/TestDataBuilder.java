package com.bufkes.service.recipe.util;

import com.bufkes.service.recipe.model.Recipe;

public class TestDataBuilder {

	public static Recipe buildRecipe() {
		Recipe recipe = new Recipe();
		recipe.setId("recipeId");
		recipe.setName("recipeName");
		recipe.setSource("recipeSource");
		recipe.setVolume("recipeVolume");
		recipe.setPage(1);
		return recipe;
	}


}
