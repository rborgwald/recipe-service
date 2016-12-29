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
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.service.RecipeService;
import com.bufkes.service.recipe.util.TestDataBuilder;
import com.bufkes.service.recipe.util.TestUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {
	
	@MockBean
	private RecipeService recipeService;
	
	@Autowired
	private MockMvc mvc;
	
	private RecipeController recipeController;
	
	public RecipeControllerTest() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void setup() {
		recipeController = new RecipeController();
		TestUtil.setField(recipeController, "recipeService", recipeService);
	}

	@Test
    public void testReadRecipe() throws Exception {
		
		Recipe recipe = TestDataBuilder.buildRecipe();

        when(recipeService.getRecipeById(anyString())).thenReturn(recipe);

        MvcResult result = this.mvc.perform(get("/recipes/recipeId")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);

        verify(recipeService).getRecipeById(anyString());
    }
	
	@Test
    public void testReadRecipe_Exception() throws Exception {

        when(recipeService.getRecipeById(anyString())).thenThrow(new ServiceException(ErrorType.BAD_REQUEST, "errorMsg"));

        MvcResult result = this.mvc.perform(get("/recipes/recipeId")).andReturn();

        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(400);
        Assertions.assertThat(result.getResponse().getContentAsString().contains("errorMsg")).isEqualTo(true);

    }
	
	@Test
    public void testCreateRecipe() throws Exception {
		Recipe recipe = TestDataBuilder.buildRecipe();
		when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(recipe);

        MvcResult result = this.mvc.perform(post("/recipes").content(TestUtil.asJsonString(recipe)).contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains(recipe.getId());

        verify(recipeService).saveRecipe(any(Recipe.class));
    }

    @Test
    public void testCreateRecipe_Exception() throws Exception {

        Recipe recipe = TestDataBuilder.buildRecipe();

        doThrow(new ServiceException(ErrorType.BAD_REQUEST, "errorMsg")).when(recipeService).saveRecipe(any(Recipe.class));

        MvcResult result = this.mvc.perform(post("/recipes").content(TestUtil.asJsonString(recipe)).contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString().contains("errorMsg")).isEqualTo(true);

    }
    
    @Test
    public void testUpdateRecipe() throws Exception {
		Recipe recipe = TestDataBuilder.buildRecipe();
		when(recipeService.updateRecipe(any(Recipe.class))).thenReturn(recipe);

        MvcResult result = this.mvc.perform(put("/recipes/" + recipe.getId()).content(TestUtil.asJsonString(recipe)).contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);

        verify(recipeService).updateRecipe(any(Recipe.class));
    }

    @Test
    public void testUpdateRecipe_Exception() throws Exception {
        Recipe recipe = TestDataBuilder.buildRecipe();
        doThrow(new ServiceException(ErrorType.BAD_REQUEST, "errorMsg")).when(recipeService).updateRecipe(any(Recipe.class));

        MvcResult result = this.mvc.perform(put("/recipes/" + recipe.getId()).content(TestUtil.asJsonString(recipe)).contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString().contains("errorMsg")).isEqualTo(true);

    }
    
    @Test
    public void testUpdateRecipe_NoRecipe() throws Exception {
        MvcResult result = this.mvc.perform(put("/recipes/foobar").content(TestUtil.asJsonString(null)).contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(500);
        verify(recipeService, Mockito.never()).updateRecipe(any(Recipe.class));
    }
    
    @Test
    public void testUpdateRecipe_RecipeIdsDoNotMatch() throws Exception {
    	Recipe recipe = TestDataBuilder.buildRecipe();

        MvcResult result = this.mvc.perform(put("/recipes/foobar").content(TestUtil.asJsonString(recipe)).contentType(MediaType.APPLICATION_JSON)).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString().contains("Recipe Ids do not match")).isEqualTo(true);
        verify(recipeService, Mockito.never()).updateRecipe(any(Recipe.class));
    }
    
    @Test
    public void testDeleteRecipe() throws Exception {
        doNothing().when(recipeService).deleteRecipe(anyString());

        MvcResult result = this.mvc.perform(delete("/recipes/recipeId")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        verify(recipeService).deleteRecipe(anyString());
    }

    @Test
    public void testSaveAnswers_Exception() throws Exception {

        doThrow(new ServiceException(ErrorType.BAD_REQUEST, "errorMsg")).when(recipeService).deleteRecipe(anyString());

        MvcResult result = this.mvc.perform(delete("/recipes/recipeId")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString().contains("errorMsg")).isEqualTo(true);

    }
    
    @Test
    public void testGetRecipesByName() throws Exception {
		Recipe recipe = TestDataBuilder.buildRecipe();
		when(recipeService.getRecipesByNameLike(anyString())).thenReturn(Arrays.asList(recipe));

        MvcResult result = this.mvc.perform(get("/recipes?name=recipeName")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).contains(recipe.getId());

        verify(recipeService).getRecipesByNameLike(anyString());
    }

    @Test
    public void testGetRecipesByName_Exception() throws Exception {
    	when(recipeService.getRecipesByNameLike(anyString())).thenThrow(new ServiceException(ErrorType.BAD_REQUEST, "errorMsg"));
        MvcResult result = this.mvc.perform(get("/recipes?name=recipeName")).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString().contains("errorMsg")).isEqualTo(true);

    }
}
