package com.bufkes.service.recipe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.exception.ServiceException;
import com.bufkes.service.recipe.model.CuisineType;
import com.bufkes.service.recipe.model.MealType;
import com.bufkes.service.recipe.model.PreparationType;
import com.bufkes.service.recipe.model.ProteinType;
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.service.LookupService;
import com.bufkes.service.recipe.service.RecipeService;
import com.bufkes.service.recipe.util.TestDataBuilder;
import com.bufkes.service.recipe.util.TestUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(LookupController.class)
public class LookupControllerTest {
	
	@MockBean
	private LookupService lookupService;
	
	@Autowired
	private MockMvc mvc;
	
	private LookupController lookupController;
	
	public LookupControllerTest() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void setup() {
		lookupController = new LookupController();
		TestUtil.setField(lookupController, "lookupService", lookupService);
	}

	@Test
    public void testGetMealTypes() throws Exception {
		
		MealType mealType = TestDataBuilder.buildMealType();

        when(lookupService.getMealTypes()).thenReturn(Arrays.asList(mealType.getName()));

        MvcResult result = this.mvc.perform(get("/api/lookup/mealtypes")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);

        verify(lookupService).getMealTypes();
    }
	
	@Test
    public void testGetCuisineTypes() throws Exception {
		
		CuisineType cuisineType = TestDataBuilder.buildCuisineType();

        when(lookupService.getCuisineTypes()).thenReturn(Arrays.asList(cuisineType.getName()));

        MvcResult result = this.mvc.perform(get("/api/lookup/cuisinetypes")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);

        verify(lookupService).getCuisineTypes();
    }
	
	@Test
    public void testGetProteinTypes() throws Exception {
		
		ProteinType proteinType = TestDataBuilder.buildProteinType();

        when(lookupService.getProteinTypes()).thenReturn(Arrays.asList(proteinType.getName()));

        MvcResult result = this.mvc.perform(get("/api/lookup/proteintypes")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);

        verify(lookupService).getProteinTypes();
    }
	
	@Test
    public void testGetPreparationTypes() throws Exception {
		
		PreparationType preparationType = TestDataBuilder.buildPreparationType();

        when(lookupService.getPreparationTypes()).thenReturn(Arrays.asList(preparationType.getName()));

        MvcResult result = this.mvc.perform(get("/api/lookup/preparationtypes")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);

        verify(lookupService).getPreparationTypes();
    }

}
