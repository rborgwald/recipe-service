package com.bufkes.service.recipe.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bufkes.service.recipe.model.CuisineType;
import com.bufkes.service.recipe.model.MealType;
import com.bufkes.service.recipe.model.PreparationType;
import com.bufkes.service.recipe.model.ProteinType;
import com.bufkes.service.recipe.repository.CuisineTypeRepository;
import com.bufkes.service.recipe.repository.MealTypeRepository;
import com.bufkes.service.recipe.repository.PreparationTypeRepository;
import com.bufkes.service.recipe.repository.ProteinTypeRepository;
import com.bufkes.service.recipe.service.LookupService;


@Component("lookupService")
public class LookupServiceImpl implements LookupService {
	
	@Autowired
	private MealTypeRepository mealTypeRepository;
	
	@Autowired
	private CuisineTypeRepository cuisineTypeRepository;
	
	@Autowired
	private ProteinTypeRepository proteinTypeRepository;
	
	@Autowired
	private PreparationTypeRepository preparationTypeRepository;
	
	@Override
	public List<MealType> getMealTypes() {
		return mealTypeRepository.findAll();
	}

	@Override
	public List<CuisineType> getCuisineTypes() {
		return cuisineTypeRepository.findAll();
	}

	@Override
	public List<ProteinType> getProteinTypes() {
		return proteinTypeRepository.findAll();
	}

	@Override
	public List<PreparationType> getPreparationTypes() {
		return preparationTypeRepository.findAll();
	}

}
