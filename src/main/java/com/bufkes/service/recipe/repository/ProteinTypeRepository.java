package com.bufkes.service.recipe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bufkes.service.recipe.model.ProteinType;

public interface ProteinTypeRepository extends CrudRepository<ProteinType, Long> {

	public List<ProteinType> findAll();
	
	public ProteinType findByName(String name);
	
	public ProteinType findById(Integer id);
	
	List<ProteinType> findByNameIgnoreCase(String name);
	
	public ProteinType findFirstByOrderByIdDesc();

	public void deleteById(Integer id);
}
