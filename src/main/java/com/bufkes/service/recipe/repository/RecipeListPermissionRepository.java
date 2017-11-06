package com.bufkes.service.recipe.repository;

import com.bufkes.service.recipe.model.RecipeListPermission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecipeListPermissionRepository extends CrudRepository<RecipeListPermission, Long> {

    List<RecipeListPermission> findByUserId(long id);

    RecipeListPermission findByUserIdAndRecipeListId(long id, String recipeListId);

    List<RecipeListPermission> findByRecipeListId(String id);
}
