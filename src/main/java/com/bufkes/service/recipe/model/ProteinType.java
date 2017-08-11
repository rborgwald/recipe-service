package com.bufkes.service.recipe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "protein_type_lu")
public class ProteinType implements SearchCriterion {

	@Id
	@Column(name="id", unique=true)
	private Integer id;
	
	private String name;
	
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ProteinType [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
}
