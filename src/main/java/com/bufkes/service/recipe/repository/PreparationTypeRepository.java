package com.bufkes.service.recipe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bufkes.service.recipe.model.PreparationType;

public interface PreparationTypeRepository extends CrudRepository<PreparationType, Long> {

	public List<PreparationType> findAll();
	
	public PreparationType findByName(String name);
	
	public PreparationType findById(Integer id);
	
	List<PreparationType> findByNameIgnoreCase(String name);
	
	public PreparationType findFirstByOrderByIdDesc();
	
	public void deleteById(Integer id);
}
