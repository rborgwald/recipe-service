package com.bufkes.service.recipe.service.impl;
import static com.bufkes.service.recipe.util.Assert.isTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.model.CuisineType;
import com.bufkes.service.recipe.model.MealType;
import com.bufkes.service.recipe.model.PreparationType;
import com.bufkes.service.recipe.model.ProteinType;
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.repository.CuisineTypeRepository;
import com.bufkes.service.recipe.repository.MealTypeRepository;
import com.bufkes.service.recipe.repository.PreparationTypeRepository;
import com.bufkes.service.recipe.repository.ProteinTypeRepository;
import com.bufkes.service.recipe.repository.RecipeRepository;
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
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Override
	public List<MealType> getMealTypes() {
		return mealTypeRepository.findAll();
	}
	
	@Transactional
	@Override
	public MealType saveMealType(MealType mealType) {
		// Verify type with same name does not already exist
		isTrue(mealType != null, ErrorType.SYSTEM, "New meal type is null or empty");
		List<MealType> existingMealTypes = mealTypeRepository.findByNameIgnoreCase(mealType.getName());
		isTrue(existingMealTypes.isEmpty(), ErrorType.DATA_VALIDATION, "Meal type with same name already exists: " + mealType.getName());
		
		// Increment id
		MealType lastMealType = mealTypeRepository.findFirstByOrderByIdDesc();
		Integer newId = lastMealType.getId() + 1;
		MealType existingMealType = mealTypeRepository.findById(newId);
		isTrue(existingMealType == null, ErrorType.DATA_VALIDATION, "Id already used: " + newId);
		
		mealType.setId(newId);
		return mealTypeRepository.save(mealType);
	}
	
	@Override
	public MealType updateMealType(MealType mealType) {
		isTrue(mealType != null, ErrorType.SYSTEM, "Meal type details not available");
		MealType existingType = mealTypeRepository.findById(mealType.getId());
		isTrue(existingType != null, ErrorType.NO_DATA_FOUND, "Meal type not found");
		
		mealTypeRepository.save(mealType);
		return mealTypeRepository.findById(mealType.getId());
	}
	
	@Transactional
	public void deleteMealType(Integer id) {
		isTrue(id != null, ErrorType.SYSTEM, "Meal type id is null");
		isTrue(mealTypeRepository.findById(id) != null, ErrorType.SYSTEM, "No meal type found with id: " + id);
		isTrue(recipeRepository.findByMealTypeId(id).isEmpty(), ErrorType.SYSTEM, "Cannot delete meal type - it is referenced by recipes");
		mealTypeRepository.deleteById(id);
	}

	@Override
	public List<CuisineType> getCuisineTypes() {
		return cuisineTypeRepository.findAll();
	}
	
	@Transactional
	@Override
	public CuisineType saveCuisineType(CuisineType type) {
		// Verify type with same name does not already exist
		isTrue(type != null, ErrorType.SYSTEM, "New cuisine type is null or empty");
		List<CuisineType> existingCuisineTypes = cuisineTypeRepository.findByNameIgnoreCase(type.getName());
		isTrue(existingCuisineTypes.isEmpty(), ErrorType.DATA_VALIDATION, "Cuisine type with same name already exists: " + type.getName());
		
		// Increment id
		CuisineType lastType = cuisineTypeRepository.findFirstByOrderByIdDesc();
		Integer newId = lastType.getId() + 1;
		CuisineType existingType = cuisineTypeRepository.findById(newId);
		isTrue(existingType == null, ErrorType.DATA_VALIDATION, "Id already used: " + newId);
		
		type.setId(newId);
		return cuisineTypeRepository.save(type);
	}
	
	@Override
	public CuisineType updateCuisineType(CuisineType cuisineType) {
		isTrue(cuisineType != null, ErrorType.SYSTEM, "Cuisine type details not available");
		CuisineType existingType = cuisineTypeRepository.findById(cuisineType.getId());
		isTrue(existingType != null, ErrorType.NO_DATA_FOUND, "Cuisine type not found");
		
		cuisineTypeRepository.save(cuisineType);
		return cuisineTypeRepository.findById(cuisineType.getId());
	}
	
	@Transactional
	public void deleteCuisineType(Integer id) {
		isTrue(id != null, ErrorType.SYSTEM, "Cuisine type id is null");
		isTrue(cuisineTypeRepository.findById(id) != null, ErrorType.SYSTEM, "No cuisine type found with id: " + id);
		isTrue(recipeRepository.findByCuisineTypeId(id).isEmpty(), ErrorType.SYSTEM, "Cannot delete cuisine type - it is referenced by recipes");
		cuisineTypeRepository.deleteById(id);
	}

	@Override
	public List<ProteinType> getProteinTypes() {
		return proteinTypeRepository.findAll();
	}
	
	@Transactional
	@Override
	public ProteinType saveProteinType(ProteinType type) {
		// Verify type with same name does not already exist
		isTrue(type != null, ErrorType.SYSTEM, "New protein type is null or empty");
		List<ProteinType> existingTypes = proteinTypeRepository.findByNameIgnoreCase(type.getName());
		isTrue(existingTypes.isEmpty(), ErrorType.DATA_VALIDATION, "Protein type with same name already exists: " + type.getName());
		
		// Increment id
		ProteinType lastType = proteinTypeRepository.findFirstByOrderByIdDesc();
		Integer newId = lastType.getId() + 1;
		ProteinType existingType = proteinTypeRepository.findById(newId);
		isTrue(existingType == null, ErrorType.DATA_VALIDATION, "Id already used: " + newId);
		
		type.setId(newId);
		return proteinTypeRepository.save(type);
	}
	
	@Override
	public ProteinType updateProteinType(ProteinType proteinType) {
		isTrue(proteinType != null, ErrorType.SYSTEM, "Protein type details not available");
		ProteinType existingType = proteinTypeRepository.findById(proteinType.getId());
		isTrue(existingType != null, ErrorType.NO_DATA_FOUND, "Protein type not found");
		
		proteinTypeRepository.save(proteinType);
		return proteinTypeRepository.findById(proteinType.getId());
	}
	
	@Transactional
	public void deleteProteinType(Integer id) {
		isTrue(id != null, ErrorType.SYSTEM, "Protein type id is null");
		isTrue(proteinTypeRepository.findById(id) != null, ErrorType.SYSTEM, "No protein type found with id: " + id);
		isTrue(recipeRepository.findByProteinTypeId(id).isEmpty(), ErrorType.SYSTEM, "Cannot delete protein type - it is referenced by recipes");
		proteinTypeRepository.deleteById(id);
	}

	@Override
	public List<PreparationType> getPreparationTypes() {
		return preparationTypeRepository.findAll();
	}
	
	@Transactional
	@Override
	public PreparationType savePreparationType(PreparationType type) {
		// Verify type with same name does not already exist
		isTrue(type != null, ErrorType.SYSTEM, "New preparation type is null or empty");
		List<PreparationType> existingTypes = preparationTypeRepository.findByNameIgnoreCase(type.getName());
		isTrue(existingTypes.isEmpty(), ErrorType.DATA_VALIDATION, "Preparation type with same name already exists: " + type.getName());
		
		// Increment id
		PreparationType lastType = preparationTypeRepository.findFirstByOrderByIdDesc();
		Integer newId = lastType.getId() + 1;
		PreparationType existingType = preparationTypeRepository.findById(newId);
		isTrue(existingType == null, ErrorType.DATA_VALIDATION, "Id already used: " + newId);
		
		type.setId(newId);
		return preparationTypeRepository.save(type);
	}
	
	@Transactional
	public void deletePreparationType(Integer id) {
		isTrue(id != null, ErrorType.SYSTEM, "Preparation type id is null");
		isTrue(preparationTypeRepository.findById(id) != null, ErrorType.SYSTEM, "No preparation type found with id: " + id);
		isTrue(recipeRepository.findByPreparationTypeId(id).isEmpty(), ErrorType.SYSTEM, "Cannot delete preparation type - it is referenced by recipes");
		preparationTypeRepository.deleteById(id);
	}

	@Override
	public PreparationType updatePreparationType(PreparationType preparationType) {
		isTrue(preparationType != null, ErrorType.SYSTEM, "Preparation type details not available");
		PreparationType existingType = preparationTypeRepository.findById(preparationType.getId());
		isTrue(existingType != null, ErrorType.NO_DATA_FOUND, "Preparation type not found");
		
		preparationTypeRepository.save(preparationType);
		return preparationTypeRepository.findById(preparationType.getId());
	}

}
