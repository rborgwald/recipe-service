package com.bufkes.service.recipe.util;

import java.util.List;

import org.assertj.core.util.Lists;

import com.bufkes.service.recipe.model.CuisineType;
import com.bufkes.service.recipe.model.MealType;
import com.bufkes.service.recipe.model.PreparationType;
import com.bufkes.service.recipe.model.ProteinType;
import com.bufkes.service.recipe.model.Recipe;
import com.bufkes.service.recipe.model.User;

public class TestDataBuilder {

	public static Recipe buildRecipe() {
		Recipe recipe = new Recipe();
		recipe.setId("recipeId");
		recipe.setName("recipeName");
		recipe.setSource("recipeSource");
		recipe.setVolume("recipeVolume");
		recipe.setPage(1);
		return recipe;
	}

	public static List<MealType> buildMealTypes() {
		MealType mt1 = new MealType();
		mt1.setId(1);
		mt1.setName("FOO");
		mt1.setDescription("Foo");
		
		MealType mt2 = new MealType();
		mt2.setId(2);
		mt2.setName("BAR");
		mt2.setDescription("Bar");
		
		return Lists.newArrayList(mt1, mt2);
	}

	public static List<CuisineType> buildCuisineTypes() {
		CuisineType mt1 = new CuisineType();
		mt1.setId(1);
		mt1.setName("FOO");
		mt1.setDescription("Foo");
		
		CuisineType mt2 = new CuisineType();
		mt2.setId(2);
		mt2.setName("BAR");
		mt2.setDescription("Bar");
		
		return Lists.newArrayList(mt1, mt2);
	}
	
	public static List<ProteinType> buildProteinTypes() {
		ProteinType mt1 = new ProteinType();
		mt1.setId(1);
		mt1.setName("FOO");
		mt1.setDescription("Foo");
		
		ProteinType mt2 = new ProteinType();
		mt2.setId(2);
		mt2.setName("BAR");
		mt2.setDescription("Bar");
		
		return Lists.newArrayList(mt1, mt2);
	}
	
	public static List<PreparationType> buildPreparationTypes() {
		PreparationType mt1 = new PreparationType();
		mt1.setId(1);
		mt1.setName("FOO");
		mt1.setDescription("Foo");
		
		PreparationType mt2 = new PreparationType();
		mt2.setId(2);
		mt2.setName("BAR");
		mt2.setDescription("Bar");
		
		return Lists.newArrayList(mt1, mt2);
	}
	
	public static MealType buildMealType() {
		MealType mealType = new MealType();
        mealType.setName("DINNER");
        mealType.setDescription("Dinner");
        mealType.setId(1);
        
        return mealType;
	}

	public static CuisineType buildCuisineType() {
		CuisineType cuisineType = new CuisineType();
        cuisineType.setName("AMERICAN");
        cuisineType.setDescription("American");
        cuisineType.setId(1);
        
        return cuisineType;
	}
	
	public static ProteinType buildProteinType() {
		ProteinType proteinType = new ProteinType();
        proteinType.setName("BEEF");
        proteinType.setDescription("Beef");
        proteinType.setId(1);
        
        return proteinType;
	}
	
	public static PreparationType buildPreparationType() {
		PreparationType preparationType = new PreparationType();
        preparationType.setName("CROCKPOT");
        preparationType.setDescription("Crockpot");
        preparationType.setId(1);
        
        return preparationType;
	}

	public static User buildUser() {
        User userDetails = new User();
        userDetails.setId(1L);
        userDetails.setUsername("username");
        userDetails.setPassword("password");
        return userDetails;
    }
}
