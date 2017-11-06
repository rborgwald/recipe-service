package com.bufkes.service.recipe.repository;

import com.bufkes.service.recipe.model.RecipeList;
import org.springframework.data.repository.CrudRepository;

public interface RecipeListRepository extends CrudRepository<RecipeList, Long> {

    RecipeList findById(String recipeListId);
}
