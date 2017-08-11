package com.bufkes.service.recipe.validation;

import static com.bufkes.service.recipe.util.Assert.isTrue;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.model.CuisineType;
import com.bufkes.service.recipe.model.MealType;
import com.bufkes.service.recipe.model.PreparationType;
import com.bufkes.service.recipe.model.ProteinType;
import com.bufkes.service.recipe.model.SearchCriterion;
import com.bufkes.service.recipe.repository.CuisineTypeRepository;
import com.bufkes.service.recipe.repository.MealTypeRepository;
import com.bufkes.service.recipe.repository.PreparationTypeRepository;
import com.bufkes.service.recipe.repository.ProteinTypeRepository;

@Component
public class SearchCriteriaValidationService {
	
	@Autowired
	private MealTypeRepository mealTypeRepository;
	
	@Autowired
	private CuisineTypeRepository cuisineTypeRepository;
	
	@Autowired
	private ProteinTypeRepository proteinTypeRepository;
	
	@Autowired
	private PreparationTypeRepository preparationTypeRepository;

	
	public SearchCriterion validateCategoryAndType(String category, String type) {
		String categoryUpperCase = StringUtils.upperCase(category);
		String typeUpperCase = StringUtils.upperCase(type);
		
		if (StringUtils.equals("MEALTYPE", categoryUpperCase)) {
			MealType mealType = mealTypeRepository.findByName(typeUpperCase);
			isTrue(mealType != null, "Not a valid meal type: " + typeUpperCase);
			return mealType;
		} else if (StringUtils.equals("CUISINETYPE", categoryUpperCase)) {
			CuisineType cuisineType = cuisineTypeRepository.findByName(typeUpperCase);
			isTrue(cuisineType != null, "Not a valid cuisine type: " + typeUpperCase);
			return cuisineType;
		} else if (StringUtils.equals("PROTEINTYPE", categoryUpperCase)) {
			ProteinType proteinType = proteinTypeRepository.findByName(typeUpperCase);
			isTrue(proteinType != null, "Not a valid protein type: " + typeUpperCase);
			return proteinType;
		} else if (StringUtils.equals("PREPARATIONTYPE", categoryUpperCase)) {
			PreparationType preparationType = preparationTypeRepository.findByName(typeUpperCase);
			isTrue(preparationType != null, "Not a valid preparation type: " + typeUpperCase);
			return preparationType;
		} else {
			isTrue(false, ErrorType.DATA_VALIDATION, "Not a valid category: " + categoryUpperCase);
		}
		
		return null;
	}
	
	public SearchCriterion validateCategory(String category) {
		String categoryUpperCase = StringUtils.upperCase(category);
		
		if (StringUtils.equals("MEALTYPE", categoryUpperCase)) {
			return new MealType();
		} else if (StringUtils.equals("CUISINETYPE", categoryUpperCase)) {
			return new CuisineType();
		} else if (StringUtils.equals("PROTEINTYPE", categoryUpperCase)) {
			return new ProteinType();
		} else if (StringUtils.equals("PREPARATIONTYPE", categoryUpperCase)) {
			return new PreparationType();
		} else {
			isTrue(false, ErrorType.DATA_VALIDATION, "Not a valid category: " + categoryUpperCase);
		}
		
		return null;
	}

}
