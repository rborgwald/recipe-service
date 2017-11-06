package com.bufkes.service.recipe.service.impl;

import com.bufkes.service.recipe.exception.ErrorType;
import com.bufkes.service.recipe.model.*;
import com.bufkes.service.recipe.repository.*;
import com.bufkes.service.recipe.security.util.SecurityUtil;
import com.bufkes.service.recipe.service.RecipeListService;
import com.bufkes.service.recipe.service.RecipeService;
import com.bufkes.service.recipe.util.Assert;
import com.bufkes.service.recipe.validation.SearchCriteriaValidationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.bufkes.service.recipe.util.Assert.isTrue;


@Component("recipeListService")
public class RecipeListServiceImpl implements RecipeListService {

	private static final Logger LOG = LogManager.getLogger(RecipeListServiceImpl.class.getName());
	
	@Autowired
	private RecipeListRepository recipeListRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RecipeListPermissionRepository recipeListPermissionRepository;

	@Autowired
    private RecipeListMappingRepository recipeListMappingRepository;

	@Autowired
    private RecipeRepository recipeRepository;

	@Override
	public List<RecipeList> getRecipeLists() {
		return (List<RecipeList>) recipeListRepository.findAll();
	}

	@Override
	public List<RecipeList> getRecipeListsForUser(String username) {
		User user = userRepository.findByUsernameIgnoreCase(username);
		Assert.isTrue(user != null, ErrorType.NO_DATA_FOUND, "User does not exist");
		List<RecipeListPermission> permissions = recipeListPermissionRepository.findByUserId(user.getId());
		return permissions.stream().map(RecipeListPermission::getRecipeList).collect(Collectors.toList());
	}

    @Override
    public List<Recipe> getRecipesForList(String recipeListId) {
        List<RecipeListMapping> recipeListMappings = recipeListMappingRepository.findByRecipeListId(recipeListId);
        return recipeListMappings.stream().map(RecipeListMapping::getRecipe).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RecipeList createRecipeList(RecipeList recipeList) {
        String username = SecurityUtil.getUserFromHeader();
        Assert.isTrue(StringUtils.isNotBlank(username), ErrorType.NO_DATA_FOUND, "No user found in jwt. Try authenticating again.");
        User user = userRepository.findByUsernameIgnoreCase(username);
        Assert.isTrue(user != null, ErrorType.NO_DATA_FOUND, "No user found");

        Assert.isTrue(StringUtils.isNotBlank(recipeList.getName()), ErrorType.DATA_VALIDATION, "List name cannot be empty");
        recipeList = recipeListRepository.save(recipeList);

        RecipeListPermission recipeListPermission = recipeListPermissionRepository.findByUserIdAndRecipeListId(user.getId(), recipeList.getId());
        if (recipeListPermission == null) {
            recipeListPermission = new RecipeListPermission();
            recipeListPermission.setRecipeList(recipeList);
            recipeListPermission.setUser(user);
            recipeListPermissionRepository.save(recipeListPermission);
        }

        return recipeList;
    }

    @Override
    @Transactional
    public void deleteRecipeList(String id) {
        RecipeList recipeList = recipeListRepository.findById(id);
        Assert.isTrue(recipeList != null, ErrorType.NO_DATA_FOUND, "Recipe list not found");
        // Remove associated recipeListMappings
        List<RecipeListMapping> mappings = recipeListMappingRepository.findByRecipeListId(id);
        for (RecipeListMapping mapping : mappings) {
            recipeListMappingRepository.delete(mapping);
        }
        // Remove associated recipeListPermissions
        List<RecipeListPermission> permissions = recipeListPermissionRepository.findByRecipeListId(id);
        for(RecipeListPermission permission : permissions) {
            recipeListPermissionRepository.delete(permission);
        }

        recipeListRepository.delete(recipeList);
    }

    @Override
    public RecipeListMapping addRecipeToList(String recipeListId, String recipeId) {
        RecipeList recipeList = recipeListRepository.findById(recipeListId);
        Assert.isTrue(recipeList != null, ErrorType.NO_DATA_FOUND, "Recipe list not found");
        RecipeListMapping recipeListMapping = recipeListMappingRepository.findByRecipeListIdAndRecipeId(recipeListId, recipeId);
        if (recipeListMapping == null) {
            Recipe recipe = recipeRepository.findById(recipeId);
            Assert.isTrue(recipe != null, ErrorType.NO_DATA_FOUND, "Recipe not found");
            recipeListMapping = new RecipeListMapping();
            recipeListMapping.setRecipeList(recipeList);
            recipeListMapping.setRecipe(recipe);
            return recipeListMappingRepository.save(recipeListMapping);
        } else {
            return recipeListMapping;
        }
    }

    @Override
    public RecipeListPermission addUserToRecipeList(String recipeListId, long userId) {
        RecipeList recipeList = recipeListRepository.findById(recipeListId);
        Assert.isTrue(recipeList != null, ErrorType.NO_DATA_FOUND, "Recipe list not found");
        RecipeListPermission permission = recipeListPermissionRepository.findByUserIdAndRecipeListId(userId, recipeListId);
        if (permission == null) {
            User user = userRepository.findById(userId);
            Assert.isTrue(user != null, ErrorType.NO_DATA_FOUND, "No user found");
            permission = new RecipeListPermission();
            permission.setUser(user);
            permission.setRecipeList(recipeList);
            return recipeListPermissionRepository.save(permission);
        } else {
            return permission;
        }
    }

    @Override
    public void removeUserFromList(String recipeListId, long userId) {
        RecipeList recipeList = recipeListRepository.findById(recipeListId);
        Assert.isTrue(recipeList != null, ErrorType.NO_DATA_FOUND, "Recipe list not found");
        RecipeListPermission permission = recipeListPermissionRepository.findByUserIdAndRecipeListId(userId, recipeListId);
        if (permission != null) {
            recipeListPermissionRepository.delete(permission);
        }
    }

    @Override
    @Transactional
    public void removeRecipeFromList(String recipeListId, String recipeId) {
        RecipeList recipeList = recipeListRepository.findById(recipeListId);
        Assert.isTrue(recipeList != null, ErrorType.NO_DATA_FOUND, "Recipe list not found");
        RecipeListMapping recipeListMapping = recipeListMappingRepository.findByRecipeListIdAndRecipeId(recipeListId, recipeId);
        if (recipeListMapping != null) {
            recipeListMappingRepository.delete(recipeListMapping);
        }
    }
}
