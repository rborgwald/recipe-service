package com.bufkes.service.recipe.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.exception.ServiceException;
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.repository.RecipeRepository;
import com.bufkes.service.recipe.service.RecipeService;
import com.bufkes.service.recipe.util.TestDataBuilder;
import com.bufkes.service.recipe.util.TestUtil;

public class RecipeServiceImplTest {

	private static final String ERROR_TYPE_NAME_KEY = "errorType.name";
	private static final String SERVICE_ERROR_MSG_KEY = "serviceError.errorMessage";
	
	@Mock
	private RecipeRepository recipeRepository;
	
	private RecipeService recipeService;
	
	public RecipeServiceImplTest() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void setup() {
		recipeService = new RecipeServiceImpl();
		TestUtil.setField(recipeService, "recipeRepository", recipeRepository);
    }
	
	@Test
	public void testSaveRecipe_EmptyRecipe() {
		Assertions.assertThatThrownBy(() -> recipeService.saveRecipe(null))
				.isExactlyInstanceOf(ServiceException.class).extracting(ERROR_TYPE_NAME_KEY, SERVICE_ERROR_MSG_KEY)
				.containsExactly(ErrorType.SYSTEM.toString(), "Recipe is null or empty");
	}
	
	@Test
	public void testSaveRecipe() {
		Recipe recipe = TestDataBuilder.buildRecipe();
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        verify(recipeRepository).save(recipe);
        assertThat(savedRecipe).isNotNull();
	}
	
	@Test
	public void testGetRecipeById_BlankId() {
		Assertions.assertThatThrownBy(() -> recipeService.getRecipeById(""))
		.isExactlyInstanceOf(ServiceException.class).extracting(ERROR_TYPE_NAME_KEY, SERVICE_ERROR_MSG_KEY)
		.containsExactly(ErrorType.SYSTEM.toString(), "Recipe id is null or empty");
	}
	
	@Test
	public void testGetRecipeById_NullId() {
		Assertions.assertThatThrownBy(() -> recipeService.getRecipeById(null))
		.isExactlyInstanceOf(ServiceException.class).extracting(ERROR_TYPE_NAME_KEY, SERVICE_ERROR_MSG_KEY)
		.containsExactly(ErrorType.SYSTEM.toString(), "Recipe id is null or empty");
	}
	
	@Test
	public void testGetRecipeById_RecipeNotFound() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		when(recipeRepository.findById(anyString())).thenReturn(null);
		
		Recipe nullRecipe = recipeService.getRecipeById(recipe.getId());
		
		verify(recipeRepository).findById(recipe.getId());
		assertThat(nullRecipe).isNull();
	}
	
	@Test
	public void testGetRecipeById() {
		Recipe recipe = TestDataBuilder.buildRecipe();
        when(recipeRepository.findById(anyString())).thenReturn(recipe);

        Recipe savedRecipe = recipeService.getRecipeById(recipe.getId());

        verify(recipeRepository).findById(anyString());
        assertThat(savedRecipe).isNotNull();
	}
	
	@Test
	public void testUpdateRecipe_NullRecipe() {
		Assertions.assertThatThrownBy(() -> recipeService.updateRecipe(null))
		.isExactlyInstanceOf(ServiceException.class).extracting(ERROR_TYPE_NAME_KEY, SERVICE_ERROR_MSG_KEY)
		.containsExactly(ErrorType.SYSTEM.toString(), "Recipe details not available");
	}
	
	@Test
	public void testUpdateRecipe_NoRecipeFound() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		when(recipeRepository.findById(anyString())).thenReturn(null);
		
		Assertions.assertThatThrownBy(() -> recipeService.updateRecipe(recipe))
		.isExactlyInstanceOf(ServiceException.class).extracting(ERROR_TYPE_NAME_KEY, SERVICE_ERROR_MSG_KEY)
		.containsExactly(ErrorType.NO_DATA_FOUND.toString(), "Recipe not found");
		
		verify(recipeRepository).findById(anyString());
	}
	
	@Test
	public void testUpdateRecipe() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		when(recipeRepository.findById(anyString())).thenReturn(recipe);
		when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
		
		Recipe updatedRecipe = recipeService.updateRecipe(recipe);
		
		verify(recipeRepository, Mockito.times(2)).findById(anyString());
		verify(recipeRepository).save(any(Recipe.class));
		assertThat(updatedRecipe).isNotNull();
	}
	
	@Test
	public void testDeleteRecipe_BlankId() {
		Assertions.assertThatThrownBy(() -> recipeService.deleteRecipe(""))
		.isExactlyInstanceOf(ServiceException.class).extracting(ERROR_TYPE_NAME_KEY, SERVICE_ERROR_MSG_KEY)
		.containsExactly(ErrorType.SYSTEM.toString(), "Recipe id is null or empty");
	}
	
	@Test
	public void testDeleteRecipe_NullId() {
		Assertions.assertThatThrownBy(() -> recipeService.deleteRecipe(null))
		.isExactlyInstanceOf(ServiceException.class).extracting(ERROR_TYPE_NAME_KEY, SERVICE_ERROR_MSG_KEY)
		.containsExactly(ErrorType.SYSTEM.toString(), "Recipe id is null or empty");
	}
	
	@Test
	public void testDeleteRecipe() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		doNothing().when(recipeRepository).deleteById(anyString());
		
		recipeService.deleteRecipe(recipe.getId());
		verify(recipeRepository).deleteById(anyString());
	}
	
	@Test
	public void testGetRecipesByNameLike_BlankName() {
		Assertions.assertThatThrownBy(() -> recipeService.getRecipesByNameLike(""))
		.isExactlyInstanceOf(ServiceException.class).extracting(ERROR_TYPE_NAME_KEY, SERVICE_ERROR_MSG_KEY)
		.containsExactly(ErrorType.SYSTEM.toString(), "Recipe name is null or empty");
	}
	
	@Test
	public void testGetRecipesByNameLike_NullName() {
		Assertions.assertThatThrownBy(() -> recipeService.getRecipesByNameLike(null))
		.isExactlyInstanceOf(ServiceException.class).extracting(ERROR_TYPE_NAME_KEY, SERVICE_ERROR_MSG_KEY)
		.containsExactly(ErrorType.SYSTEM.toString(), "Recipe name is null or empty");
	}
	
	@Test
	public void testGetRecipesByNameLike() {
		Recipe recipe = TestDataBuilder.buildRecipe();
		when(recipeRepository.findByNameContainingIgnoreCase(anyString())).thenReturn(Arrays.asList(recipe));
		
		List<Recipe> returnedList = recipeService.getRecipesByNameLike(recipe.getId());
		verify(recipeRepository).findByNameContainingIgnoreCase(anyString());
		assertThat(returnedList).isNotNull();
	}
	
	@Test
	public void testGetAllRecipes() {
		Recipe recipe = TestDataBuilder.buildRecipe();
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));

        List<Recipe> allRecipes = recipeService.getAllRecipes();

        verify(recipeRepository).findAll();
        assertThat(allRecipes).isNotEmpty();
	}
}
