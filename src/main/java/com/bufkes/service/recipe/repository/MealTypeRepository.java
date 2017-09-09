package com.bufkes.service.recipe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bufkes.service.recipe.model.MealType;

public interface MealTypeRepository extends CrudRepository<MealType, Long> {

	public List<MealType> findAll();
	
	public MealType findById(Integer id);
	
	public MealType findByName(String name);
	
	List<MealType> findByNameIgnoreCase(String name);
	
	public MealType findFirstByOrderByIdDesc();

	public void deleteById(Integer id);
}
