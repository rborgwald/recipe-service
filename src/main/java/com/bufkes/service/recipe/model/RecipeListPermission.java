package com.bufkes.service.recipe.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "RecipeListPermission")
@Table(name = "recipe_list_permission")
public class RecipeListPermission {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "recipe_list_id", nullable = false, insertable = true, updatable = true)
    private RecipeList recipeList;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "user_id", nullable = false, insertable = true, updatable = true)
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RecipeList getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(RecipeList recipeList) {
        this.recipeList = recipeList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
