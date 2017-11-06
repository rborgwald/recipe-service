package com.bufkes.service.recipe.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "RecipeListMapping")
@Table(name = "recipe_list_mapping")
public class RecipeListMapping {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "recipe_id", nullable = false, insertable = true, updatable = true)
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "recipe_list_id", nullable = false, insertable = true, updatable = true)
    private RecipeList recipeList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public RecipeList getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(RecipeList recipeList) {
        this.recipeList = recipeList;
    }
}
