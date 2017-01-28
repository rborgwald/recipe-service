package com.bufkes.service.recipe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bufkes.service.recipe.model.ProteinType;

public interface ProteinTypeRepository extends CrudRepository<ProteinType, Long> {

	public List<ProteinType> findAll();
}
