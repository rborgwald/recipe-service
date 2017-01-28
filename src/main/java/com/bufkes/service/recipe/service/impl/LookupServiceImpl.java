package com.bufkes.service.recipe.service.impl;
import java.util.List;
import java.util.stream.Collectors;

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
	public List<String> getMealTypes() {
		List<MealType> mealTypes = mealTypeRepository.findAll();
		return mealTypes.stream().map(mealType -> mealType.getName()).collect(Collectors.toList());
	}

	@Override
	public List<String> getCuisineTypes() {
		List<CuisineType> cuisineTypes = cuisineTypeRepository.findAll();
		return cuisineTypes.stream().map(cuisineType -> cuisineType.getName()).collect(Collectors.toList());
	}

	@Override
	public List<String> getProteinTypes() {
		List<ProteinType> proteinTypes = proteinTypeRepository.findAll();
		return proteinTypes.stream().map(proteinType -> proteinType.getName()).collect(Collectors.toList());
	}

	@Override
	public List<String> getPreparationTypes() {
		List<PreparationType> preparationTypes = preparationTypeRepository.findAll();
		return preparationTypes.stream().map(preparationType -> preparationType.getName()).collect(Collectors.toList());
	}

}
