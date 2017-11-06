package com.bufkes.service.recipe.repository;

import com.bufkes.service.recipe.model.RecipeListMapping;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeListMappingRepository extends CrudRepository<RecipeListMapping, Long> {

    List<RecipeListMapping> findByRecipeListId(String recipeListId);

    RecipeListMapping findByRecipeListIdAndRecipeId(String recipeListId, String recipeId);
}
