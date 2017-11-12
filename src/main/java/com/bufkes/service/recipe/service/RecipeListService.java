package com.bufkes.service.recipe.service;

import com.bufkes.service.recipe.model.*;

import java.util.List;

public interface RecipeListService {


	List<RecipeList> getRecipeLists();

    List<RecipeList> getRecipeListsForUser(String username);

    List<Recipe> getRecipesForList(String recipeListId);

    RecipeList createRecipeList(RecipeList recipeList);

    void deleteRecipeList(String id);

    List<User> getUsersForRecipeList(String recipeListId);

    RecipeList addRecipeToList(String recipeListId, String recipeId);

    void removeRecipeFromList(String recipeListId, String recipeId);

    RecipeList addUserToRecipeList(String recipeListId, long userId);

    void removeUserFromList(String recipeListId, long userId);
}
