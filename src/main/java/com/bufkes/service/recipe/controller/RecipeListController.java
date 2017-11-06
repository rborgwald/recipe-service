package com.bufkes.service.recipe.controller;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.model.*;
import com.bufkes.service.recipe.service.RecipeListService;
import com.bufkes.service.recipe.service.RecipeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bufkes.service.recipe.util.Assert.isTrue;

@RestController
@RequestMapping("${gateway.api.prefix}/recipelists")
public class RecipeListController {

	@Autowired
	private RecipeListService recipeListService;

	static final Logger LOG = LogManager.getLogger(RecipeListController.class.getName());

	@GetMapping()
	public List<RecipeList> getRecipeListsForUser(@RequestParam(value = "username") String username) {
		return recipeListService.getRecipeListsForUser(username);
	}

	@PostMapping()
    public RecipeList createRecipeList(@RequestBody RecipeList recipeList) {
	    return recipeListService.createRecipeList(recipeList);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipeList(@PathVariable String id) {
        recipeListService.deleteRecipeList(id);
    }

	@GetMapping("/{id}")
    public List<Recipe> getRecipesForList(@PathVariable String id) {
	    return recipeListService.getRecipesForList(id);
    }

    @PutMapping("/{recipeListId}/recipes/{recipeId}")
    public RecipeListMapping addRecipeToList(@PathVariable String recipeListId, @PathVariable String recipeId) {
	    return recipeListService.addRecipeToList(recipeListId, recipeId);
    }

    @PutMapping("/{recipeListId}/users/{userId}")
    public RecipeListPermission addUserPermissionToRecipeList(@PathVariable String recipeListId, @PathVariable long userId) {
	    return recipeListService.addUserToRecipeList(recipeListId, userId);
    }

    @DeleteMapping("/{recipeListId}/recipes/{recipeId}")
    public void removeRecipeFromList(@PathVariable String recipeListId, @PathVariable String recipeId) {
        recipeListService.removeRecipeFromList(recipeListId, recipeId);
    }

    @DeleteMapping("/{recipeListId}/users/{userId}")
    public void removeUserFromList(@PathVariable String recipeListId, @PathVariable long userId) {
	    recipeListService.removeUserFromList(recipeListId, userId);
    }
}
