package com.bufkes.service.recipe.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bufkes.service.recipe.model.CuisineType;
import com.bufkes.service.recipe.model.MealType;
import com.bufkes.service.recipe.model.PreparationType;
import com.bufkes.service.recipe.model.ProteinType;
import com.bufkes.service.recipe.service.LookupService;

@RestController
@RequestMapping("${gateway.api.prefix}/lookup")
public class LookupController {

	@Autowired
	private LookupService lookupService;

	static final Logger logger = LogManager.getLogger(LookupController.class.getName());

	@RequestMapping(value = "/mealtypes", method = RequestMethod.GET)
	public List<MealType> getMealTypes() {
		return lookupService.getMealTypes();
	}
	
	@RequestMapping(value = "/mealtypes", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public MealType createMealType(@RequestBody MealType mealType) {
		return lookupService.saveMealType(mealType);
	}
	
	@RequestMapping(value = "/mealtypes/{id}", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteMealType(@PathVariable Integer id) {
		lookupService.deleteMealType(id);
	}

	@RequestMapping(value = "/cuisinetypes", method = RequestMethod.GET)
	public List<CuisineType> getCuisineTypes() {
		return lookupService.getCuisineTypes();
	}
	
	@RequestMapping(value = "/cuisinetypes", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public CuisineType createCuisineType(@RequestBody CuisineType cuisineType) {
		return lookupService.saveCuisineType(cuisineType);
	}
	
	@RequestMapping(value = "/cuisinetypes/{id}", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteCuisineType(@PathVariable Integer id) {
		lookupService.deleteCuisineType(id);
	}
	
	@RequestMapping(value = "/proteintypes", method = RequestMethod.GET)
	public List<ProteinType> getProteinTypes() {
		return lookupService.getProteinTypes();
	}
	
	@RequestMapping(value = "/proteintypes", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ProteinType createProteinType(@RequestBody ProteinType proteinType) {
		return lookupService.saveProteinType(proteinType);
	}
	
	@RequestMapping(value = "/proteintypes/{id}", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteProteinType(@PathVariable Integer id) {
		lookupService.deleteProteinType(id);
	}
	
	@RequestMapping(value = "/preparationtypes", method = RequestMethod.GET)
	public List<PreparationType> getPreparationTypes() {
		return lookupService.getPreparationTypes();
	}
	
	@RequestMapping(value = "/preparationtypes", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public PreparationType createPreparationType(@RequestBody PreparationType preparationType) {
		return lookupService.savePreparationType(preparationType);
	}
	
	@RequestMapping(value = "/preparationtypes/{id}", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void deletePreparationType(@PathVariable Integer id) {
		lookupService.deletePreparationType(id);
	}
}
