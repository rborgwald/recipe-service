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

	MealType saveMealType(MealType mealType);
	
	CuisineType saveCuisineType(CuisineType cuisineType);
	
	ProteinType saveProteinType(ProteinType proteinType);

	PreparationType savePreparationType(PreparationType preparationType);

	void deleteMealType(Integer id);
	
	void deleteCuisineType(Integer id);
	
	void deleteProteinType(Integer id);
	
	void deletePreparationType(Integer id);
}
