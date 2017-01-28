package com.bufkes.service.recipe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bufkes.service.recipe.model.CuisineType;

public interface CuisineTypeRepository extends CrudRepository<CuisineType, Long> {

	public List<CuisineType> findAll();
}
