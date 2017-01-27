package com.bufkes.service.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bufkes.service.recipe.model.*;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	Recipe findById(String id);

	void deleteById(String id);
	
	List<Recipe> findByNameContainingIgnoreCase(String name);
	
	@Query("SELECT r FROM Recipe r inner join r.mealType mt WHERE mt.name = ?1")
	List<Recipe> findByMealTypeName(String name);
	
	@Query("SELECT r FROM Recipe r inner join r.cuisineType ct WHERE ct.name = ?1")
	List<Recipe> findByCuisineTypeName(String name);
	
	@Query("SELECT r FROM Recipe r inner join r.proteinType pt WHERE pt.name = ?1")
	List<Recipe> findByProteinTypeName(String name);
	
	@Query("SELECT r FROM Recipe r inner join r.preparationType pt WHERE pt.name = ?1")
	List<Recipe> findByPreparationTypeName(String name);
}
