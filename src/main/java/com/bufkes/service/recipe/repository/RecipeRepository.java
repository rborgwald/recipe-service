package com.bufkes.service.recipe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bufkes.service.recipe.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	Recipe findById(String id);

	void deleteById(String id);
	
	List<Recipe> findByNameContainingIgnoreCase(String name);
}
