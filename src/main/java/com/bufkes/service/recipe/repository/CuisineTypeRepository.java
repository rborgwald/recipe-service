package com.bufkes.service.recipe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bufkes.service.recipe.model.CuisineType;

public interface CuisineTypeRepository extends CrudRepository<CuisineType, Long> {

	public List<CuisineType> findAll();
	
	public CuisineType findByName(String name);
	
	public CuisineType findById(Integer id);
	
	List<CuisineType> findByNameIgnoreCase(String name);
	
	public CuisineType findFirstByOrderByIdDesc();
	
	public void deleteById(Integer id);
}
