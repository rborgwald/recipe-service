package com.bufkes.service.recipe.service;

import java.util.List;

public interface LookupService {

	List<String> getMealTypes();
	
	List<String> getCuisineTypes();
	
	List<String> getProteinTypes();
	
	List<String> getPreparationTypes();

}
