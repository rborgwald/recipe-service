package com.bufkes.service.recipe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "recipe")
public class Recipe {

	@Id
	@Column(name="id",unique=true)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	private String name;
	
	private String source;
	
	private String volume;
	
	private Integer page;
	
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
	
	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", source=" + source + ", volume=" + volume + ", page=" + page
				+ "]";
	}
}
