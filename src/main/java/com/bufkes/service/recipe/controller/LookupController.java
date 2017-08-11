package com.bufkes.service.recipe.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = "/cuisinetypes", method = RequestMethod.GET)
	public List<CuisineType> getCuisineTypes() {
		return lookupService.getCuisineTypes();
	}
	
	@RequestMapping(value = "/proteintypes", method = RequestMethod.GET)
	public List<ProteinType> getProteinTypes() {
		return lookupService.getProteinTypes();
	}
	
	@RequestMapping(value = "/preparationtypes", method = RequestMethod.GET)
	public List<PreparationType> getPreparationTypes() {
		return lookupService.getPreparationTypes();
	}
}
