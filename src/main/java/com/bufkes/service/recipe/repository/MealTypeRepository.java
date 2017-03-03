package com.bufkes.service.recipe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bufkes.service.recipe.model.MealType;

public interface MealTypeRepository extends CrudRepository<MealType, Long> {

	public List<MealType> findAll();
	
	public MealType findByName(String name);
}
