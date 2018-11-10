package com.bufkes.service.recipe.service.impl;
import static com.bufkes.service.recipe.util.Assert.isTrue;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.io.ByteStreams;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.model.SearchCriterion;
import com.bufkes.service.recipe.repository.RecipeRepository;
import com.bufkes.service.recipe.service.RecipeService;
import com.bufkes.service.recipe.validation.SearchCriteriaValidationService;
import org.springframework.web.multipart.MultipartFile;


@Component("recipeService")
public class RecipeServiceImpl implements RecipeService {

	private static final Logger LOG = LogManager.getLogger(RecipeServiceImpl.class.getName());
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private SearchCriteriaValidationService searchCriteriaValidationService;

	@Value("${directory.images}")
	private String imagesDirectory;
	
	@Transactional
	public Recipe saveRecipe(Recipe recipe) {
		isTrue(recipe != null, ErrorType.SYSTEM, "Recipe is null or empty");
		
		List<Recipe> existingRecipes = recipeRepository.findByNameIgnoreCase(recipe.getName());
		
		isTrue(existingRecipes.size() == 0, ErrorType.DATA_VALIDATION, "Recipe with same name already exists: " + recipe.getName());
		isTrue(recipe.getStars() == null || (recipe.getStars() > 0 && recipe.getStars() <= 3), ErrorType.DATA_VALIDATION, "Recipe stars should be between 1 - 3");
		
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
		isTrue(recipe.getStars() == null || (recipe.getStars() > 0 && recipe.getStars() <= 3), ErrorType.DATA_VALIDATION, "Recipe stars should be between 1 - 3");

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
			String proteinType, Integer stars, Boolean newRecipe) {
		
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
				
		List<Recipe> filteredByProteinType = StringUtils.isNotBlank(proteinType)
				? filteredByPreparationType.stream().filter(recipe -> recipe.getProteinType() != null && StringUtils.equalsIgnoreCase(proteinType, recipe.getProteinType().getName())).collect(Collectors.toList())
				: filteredByPreparationType;
				
		List<Recipe> filteredByStars = stars != null
				? filteredByProteinType.stream().filter(recipe -> recipe.getStars() != null && recipe.getStars() >= stars).collect(Collectors.toList())
				: filteredByProteinType;
				
		return newRecipe != null
				? filteredByStars.stream().filter(recipe -> recipe.getNewRecipe() != null && recipe.getNewRecipe() == newRecipe).collect(Collectors.toList())
				: filteredByStars;		
	}


	@Override
	public Recipe addImage(String recipeId, MultipartFile file) {
		Recipe recipe = recipeRepository.findById(recipeId);

		isTrue(recipe != null, ErrorType.NO_DATA_FOUND, "Recipe not found");

		String previousFilename = recipe.getImageFilename();

		String fileName = recipe.getId() + "-" + System.currentTimeMillis() + "." + file.getOriginalFilename().split("\\.")[1];
		String path = imagesDirectory + fileName;
		try {
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream =
					new BufferedOutputStream(new FileOutputStream(new File(path)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			LOG.error("Error saving image: " + e.getMessage());
			isTrue(false, ErrorType.SYSTEM, "Unable to save image");
		}

		recipe.setImageFilename(fileName);
		recipeRepository.save(recipe);

		LOG.info("Successfully saved image to " + path);

		if (StringUtils.isNotBlank(previousFilename)) {
			File previousFile = new File(imagesDirectory + previousFilename);

			if(previousFile.delete()) {
				LOG.info("Previous file successfully deleted: " + previousFilename);
			} else {
				LOG.info("Previous file not deleted: " + previousFilename);
			}
		}

		return recipeRepository.findById(recipe.getId());
	}

	@Override
	public byte[] getImage(String recipeId) throws IOException {
		Recipe recipe = recipeRepository.findById(recipeId);

		isTrue(recipe != null, ErrorType.NO_DATA_FOUND, "Recipe not found");

		String filename = recipe.getImageFilename();

		isTrue(StringUtils.isNotBlank(filename), ErrorType.NO_DATA_FOUND, "No image found for recipe");

		String path = imagesDirectory + filename;

		try {
			return Files.readAllBytes(new File(path).toPath());
		} catch (Exception e) {
			LOG.error("Error getting file: ", e.getMessage());
			throw e;
		}
	}

	@Override
	public Recipe deleteImage(String recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId);

		isTrue(recipe != null, ErrorType.NO_DATA_FOUND, "Recipe not found");

		String filename = recipe.getImageFilename();

		if (StringUtils.isNotBlank(filename)) {
			// Delete image file
			File file = new File(imagesDirectory + filename);

			if(file.delete()) {
				LOG.info("File successfully deleted: " + filename);
			} else {
				LOG.info("Unable to delete file: " + filename);
			}

			recipe.setImageFilename(null);
			recipeRepository.save(recipe);
			return recipe;
		} else {
			// No image to delete, just return recipe
			return recipe;
		}
	}
}
