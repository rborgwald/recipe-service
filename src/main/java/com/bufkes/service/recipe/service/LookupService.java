package com.bufkes.service.recipe.service;

import java.util.List;

import com.bufkes.service.recipe.model.CuisineType;
import com.bufkes.service.recipe.model.MealType;
import com.bufkes.service.recipe.model.PreparationType;
import com.bufkes.service.recipe.model.ProteinType;

public interface LookupService {

	List<MealType> getMealTypes();
	
	List<CuisineType> getCuisineTypes();
	
	List<ProteinType> getProteinTypes();
	
	List<PreparationType> getPreparationTypes();

}
