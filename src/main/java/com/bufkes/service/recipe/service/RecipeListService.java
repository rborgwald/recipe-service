package com.bufkes.service.recipe.service;

import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.model.RecipeList;
import com.bufkes.service.recipe.model.RecipeListMapping;
import com.bufkes.service.recipe.model.RecipeListPermission;

import java.util.List;

public interface RecipeListService {


	List<RecipeList> getRecipeLists();

    List<RecipeList> getRecipeListsForUser(String username);

    List<Recipe> getRecipesForList(String recipeListId);

    RecipeList createRecipeList(RecipeList recipeList);

    void deleteRecipeList(String id);

    RecipeListMapping addRecipeToList(String recipeListId, String recipeId);

    void removeRecipeFromList(String recipeListId, String recipeId);

    RecipeListPermission addUserToRecipeList(String recipeListId, long userId);

    void removeUserFromList(String recipeListId, long userId);
}
