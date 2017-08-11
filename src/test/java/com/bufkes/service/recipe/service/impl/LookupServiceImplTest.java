package com.bufkes.service.recipe.service.impl;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;

import com.bufkes.service.recipe.model.CuisineType;
import com.bufkes.service.recipe.model.MealType;
import com.bufkes.service.recipe.model.PreparationType;
import com.bufkes.service.recipe.model.ProteinType;
import com.bufkes.service.recipe.repository.CuisineTypeRepository;
import com.bufkes.service.recipe.repository.MealTypeRepository;
import com.bufkes.service.recipe.repository.PreparationTypeRepository;
import com.bufkes.service.recipe.repository.ProteinTypeRepository;
import com.bufkes.service.recipe.service.LookupService;
import com.bufkes.service.recipe.util.TestDataBuilder;
import com.bufkes.service.recipe.util.TestUtil;

public class LookupServiceImplTest {

	private LookupService lookupService;
	
	@Mock
	MealTypeRepository mealTypeRepository;
	
	@Mock
	CuisineTypeRepository cuisineTypeRepository;
	
	@Mock
	ProteinTypeRepository proteinTypeRepository;
	
	@Mock
	PreparationTypeRepository preparationTypeRepository;
	
	public LookupServiceImplTest() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void setup() {
		lookupService = new LookupServiceImpl();
		TestUtil.setField(lookupService, "mealTypeRepository", mealTypeRepository);
		TestUtil.setField(lookupService, "cuisineTypeRepository", cuisineTypeRepository);
		TestUtil.setField(lookupService, "proteinTypeRepository", proteinTypeRepository);
		TestUtil.setField(lookupService, "preparationTypeRepository", preparationTypeRepository);
	}
	
	@Test
	public void testGetMealTypes() {
		List<MealType> mealTypes = TestDataBuilder.buildMealTypes();
		when(mealTypeRepository.findAll()).thenReturn(mealTypes);
		
		List<MealType> types = lookupService.getMealTypes();
		Assert.assertEquals(mealTypes.size(), types.size());
		
		verify(mealTypeRepository).findAll();
	}
	
	@Test
	public void testGetCuisineTypes() {
		List<CuisineType> cuisineTypes = TestDataBuilder.buildCuisineTypes();
		when(cuisineTypeRepository.findAll()).thenReturn(cuisineTypes);
		
		List<CuisineType> types = lookupService.getCuisineTypes();
		Assert.assertEquals(cuisineTypes.size(), types.size());
		
		verify(cuisineTypeRepository).findAll();
	}
	
	@Test
	public void testGetProteinTypes() {
		List<ProteinType> proteinTypes = TestDataBuilder.buildProteinTypes();
		when(proteinTypeRepository.findAll()).thenReturn(proteinTypes);
		
		List<ProteinType> types = lookupService.getProteinTypes();
		Assert.assertEquals(proteinTypes.size(), types.size());
		
		verify(proteinTypeRepository).findAll();
	}
	
	@Test
	public void testGetPreparationTypes() {
		List<PreparationType> preparationTypes = TestDataBuilder.buildPreparationTypes();
		when(preparationTypeRepository.findAll()).thenReturn(preparationTypes);
		
		List<PreparationType> types = lookupService.getPreparationTypes();
		Assert.assertEquals(preparationTypes.size(), types.size());
		
		verify(preparationTypeRepository).findAll();
	}
}
