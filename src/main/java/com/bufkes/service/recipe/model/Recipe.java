package com.bufkes.service.recipe.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recipe")
public class Recipe {

	@Id
	@Column(name = "id", unique = true)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	private String name;

	private String source;

	private String volume;

	private Integer page;
	
	private String notes;
	
	private Integer stars;
	
	@Column(name = "new")
	private Boolean newRecipe;

	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "meal_type_id", nullable = true, insertable = true, updatable = true)
	private MealType mealType;
	
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "cuisine_type_id", nullable = true, insertable = true, updatable = true)
	private CuisineType cuisineType;
	
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "preparation_type_id", nullable = true, insertable = true, updatable = true)
	private PreparationType preparationType;
	
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "protein_type_id", nullable = true, insertable = true, updatable = true)
	private ProteinType proteinType;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "recipe_list_mapping", joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "recipe_list_id", referencedColumnName = "id"))
	@JsonIgnore
	private Set<RecipeList> recipeLists;

	private String url;

	@Column(name = "image_filename")
	private String imageFilename;

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public Integer getStars() {
		return stars;
	}
	
	public void setStars(Integer stars) {
		this.stars = stars;
	}
	
	public Boolean getNewRecipe() {
		return newRecipe;
	}
	
	public void setNewRecipe(Boolean newRecipe) {
		this.newRecipe = newRecipe;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public CuisineType getCuisineType() {
		return cuisineType;
	}

	public void setCuisineType(CuisineType cuisineType) {
		this.cuisineType = cuisineType;
	}

	public PreparationType getPreparationType() {
		return preparationType;
	}

	public void setPreparationType(PreparationType preparationType) {
		this.preparationType = preparationType;
	}

	public ProteinType getProteinType() {
		return proteinType;
	}

	public void setProteinType(ProteinType proteinType) {
		this.proteinType = proteinType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageFilename() { return imageFilename; }

	public void setImageFilename(String imageFilename) { this.imageFilename = imageFilename; }


	public void setCriterion(SearchCriterion criterion) {
		if (criterion instanceof MealType) {
			MealType type = (MealType) criterion;
			this.setMealType(type);
		} else if (criterion instanceof CuisineType) {
			CuisineType type = (CuisineType) criterion;
			this.setCuisineType(type);
		} else if (criterion instanceof ProteinType) {
			ProteinType type = (ProteinType) criterion;
			this.setProteinType(type);
		} else if (criterion instanceof PreparationType) {
			PreparationType type = (PreparationType) criterion;
			this.setPreparationType(type);
		}
	}
	
	public void removeCriterion(SearchCriterion criterion) {
		if (criterion instanceof MealType) {
			this.setMealType(null);
		} else if (criterion instanceof CuisineType) {
			this.setCuisineType(null);
		} else if (criterion instanceof ProteinType) {
			this.setProteinType(null);
		} else if (criterion instanceof PreparationType) {
			this.setPreparationType(null);
		}
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", source=" + source + ", volume=" + volume + ", page=" + page
				+ ", notes=" + notes + ", stars=" + stars + ", newRecipe=" + newRecipe + ", mealType=" + mealType
				+ ", cuisineType=" + cuisineType + ", preparationType=" + preparationType + ", proteinType="
				+ proteinType + "]";
	}

}
