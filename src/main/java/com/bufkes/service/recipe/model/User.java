package com.bufkes.service.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String username;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "recipe_list_permission", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "recipe_list_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<RecipeList> recipeLists;

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
    	this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RecipeList> getRecipeLists() {
        return recipeLists;
    }

    public void setRecipeLists(Set<RecipeList> recipeLists) {
        this.recipeLists = recipeLists;
    }
}
