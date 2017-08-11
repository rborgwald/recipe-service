package com.bufkes.service.recipe.service.impl;
import static com.bufkes.service.recipe.util.Assert.isTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.model.SearchCriterion;
import com.bufkes.service.recipe.repository.RecipeRepository;
import com.bufkes.service.recipe.service.RecipeService;
import com.bufkes.service.recipe.validation.SearchCriteriaValidationService;


@Component("recipeService")
public class RecipeServiceImpl implements RecipeService {

	private static final Logger LOG = LogManager.getLogger(RecipeServiceImpl.class.getName());
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private SearchCriteriaValidationService searchCriteriaValidationService;
	
	@Transactional
	public Recipe saveRecipe(Recipe recipe) {
		isTrue(recipe != null, ErrorType.SYSTEM, "Recipe is null or empty");
		List<Recipe> existingRecipes = recipeRepository.findByNameIgnoreCase(recipe.getName());
		isTrue(existingRecipes.size() == 0, ErrorType.DATA_VALIDATION, "Recipe with same name already exists: " + recipe.getName());
		
		return recipeRepository.save(recipe);
	}

	@Transactional
	public Recipe getRecipeById(String id) {
		isTrue(!StringUtils.isEmpty(id), ErrorType.SYSTEM, "Recipe id is null or empty");
		Recipe recipe = recipeRepository.findById(id);
		
		if (recipe == null) {
			String errorMsg = "No recipe found for id: " + id;
			LOG.error(errorMsg);
			return null;
		} else {
			return recipe;
		}
	}

	@Transactional
	public Recipe updateRecipe(Recipe recipe) {
		isTrue(recipe != null, ErrorType.SYSTEM, "Recipe details not available");
		Recipe existingRecipe = recipeRepository.findById(recipe.getId());
		isTrue(existingRecipe != null, ErrorType.NO_DATA_FOUND, "Recipe not found");
		
		recipeRepository.save(recipe);
		return recipeRepository.findById(recipe.getId());
	}

	@Transactional
	public void deleteRecipe(String recipeId) {
		isTrue(!StringUtils.isEmpty(recipeId), ErrorType.SYSTEM, "Recipe id is null or empty");
		recipeRepository.deleteById(recipeId);
	}
	
	@Transactional
	public Recipe addSearchCriterion(String recipeId, String category, String type) {
		LOG.info("Adding search criterion: " + recipeId + " " + category + " " + type);
		isTrue(StringUtils.isNotBlank(recipeId), ErrorType.SYSTEM, "Recipe id is null or empty");
		isTrue(StringUtils.isNotBlank(category), ErrorType.SYSTEM, "Category is null or empty");
		
		Recipe existingRecipe = recipeRepository.findById(recipeId);
		isTrue(existingRecipe != null, ErrorType.NO_DATA_FOUND, "Recipe not found");
		
		if (StringUtils.isNotBlank(type)) {
			SearchCriterion criterion = searchCriteriaValidationService.validateCategoryAndType(category, type);
			existingRecipe.setCriterion(criterion);
		} else {
			SearchCriterion criterion = searchCriteriaValidationService.validateCategory(category);
			existingRecipe.removeCriterion(criterion);
		}
		
		return recipeRepository.save(existingRecipe);
	}

	@Override
	public List<Recipe> getRecipesByNameLike(String name) {
		isTrue(!StringUtils.isEmpty(name), ErrorType.SYSTEM, "Recipe name is null or empty");
		return recipeRepository.findByNameContainingIgnoreCase(name);
	}
	
	@Override
	public List<Recipe> getRecipesByMealType(String mealType) {
		isTrue(!StringUtils.isEmpty(mealType), ErrorType.SYSTEM, "Meal type is null or empty");
		return recipeRepository.findByMealTypeName(StringUtils.upperCase(mealType));
	}
	
	@Override
	public List<Recipe> getRecipesByCuisineType(String cuisineType) {
		isTrue(!StringUtils.isEmpty(cuisineType), ErrorType.SYSTEM, "Cuisine type is null or empty");
		return recipeRepository.findByCuisineTypeName(StringUtils.upperCase(cuisineType));
	}
	
	@Override
	public List<Recipe> getRecipesByProteinType(String proteinType) {
		isTrue(!StringUtils.isEmpty(proteinType), ErrorType.SYSTEM, "Protein type is null or empty");
		return recipeRepository.findByProteinTypeName(StringUtils.upperCase(proteinType));
	}
	
	@Override
	public List<Recipe> getRecipesByPreparationType(String preparationType) {
		isTrue(!StringUtils.isEmpty(preparationType), ErrorType.SYSTEM, "Preparation type is null or empty");
		return recipeRepository.findByPreparationTypeName(StringUtils.upperCase(preparationType));
	}

	@Override
	public List<Recipe> getAllRecipes() {
		 return (List<Recipe>) recipeRepository.findAll();
	}

	@Override
	public List<Recipe> findRecipes(String name, String mealType, String cuisineType, String preparationType,
			String proteinType) {
		
		List<Recipe> recipesByName =  StringUtils.isNotBlank(name) ? getRecipesByNameLike(name) : getAllRecipes();
		
		List<Recipe> filteredByMealType = StringUtils.isNotBlank(mealType) 
				? recipesByName.stream().filter(recipe -> recipe.getMealType() != null && StringUtils.equalsIgnoreCase(mealType, recipe.getMealType().getName())).collect(Collectors.toList())
				: recipesByName; 

		List<Recipe> filteredByCuisineType = StringUtils.isNotBlank(cuisineType)
				? filteredByMealType.stream().filter(recipe -> recipe.getCuisineType() != null && StringUtils.equalsIgnoreCase(cuisineType, recipe.getCuisineType().getName())).collect(Collectors.toList())
				: filteredByMealType;
				
		List<Recipe> filteredByPreparationType = StringUtils.isNotBlank(preparationType)
				? filteredByCuisineType.stream().filter(recipe -> recipe.getPreparationType() != null && StringUtils.equalsIgnoreCase(preparationType, recipe.getPreparationType().getName())).collect(Collectors.toList())
				: filteredByCuisineType;
				
		return StringUtils.isNotBlank(proteinType)
				? filteredByPreparationType.stream().filter(recipe -> recipe.getProteinType() != null && StringUtils.equalsIgnoreCase(proteinType, recipe.getProteinType().getName())).collect(Collectors.toList())
				: filteredByPreparationType;		
	}

}
