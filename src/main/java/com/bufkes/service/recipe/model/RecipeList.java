package com.bufkes.service.recipe.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "RecipeList")
@Table(name = "recipe_list")
public class RecipeList {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
