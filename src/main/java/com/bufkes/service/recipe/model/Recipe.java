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
				+ ", mealType=" + mealType + ", cuisineType=" + cuisineType + ", preparationType="
				+ preparationType + ", proteinType=" + proteinType + "]";
	}

	
}
