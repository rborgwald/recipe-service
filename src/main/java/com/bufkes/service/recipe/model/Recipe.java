package com.bufkes.service.recipe.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST }, optional = true)
	@JoinColumn(name = "meal_type_id", nullable = true, insertable = true, updatable = false)
	private MealType mealType;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST}, optional = true)
	@JoinColumn(name = "cuisine_type_id", nullable = true, insertable = true, updatable = false)
	private CuisineType cuisineType;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST}, optional = true)
	@JoinColumn(name = "preparation_type_id", nullable = true, insertable = true, updatable = false)
	private PreparationType preparationType;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST}, optional = true)
	@JoinColumn(name = "protein_type_id", nullable = true, insertable = true, updatable = false)
	private ProteinType proteinType;

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

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", source=" + source + ", volume=" + volume + ", page=" + page
				+ ", mealType=" + mealType + ", cuisineType=" + cuisineType + ", preparationType="
				+ preparationType + ", proteinType=" + proteinType + "]";
	}
}
