package com.hackathon.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Wish {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Profile profile;

	@NotBlank(message="NotBlank.wish.name")
	private String name;
	
	private String description;
	
	private String url;
	
	@NotNull(message="NotNull.wish.totalvalue")
	private Double totalValue;
	
	private Date dueDate;
	
	private Date creationDate;
	
	@OneToMany(mappedBy="wish", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Contribution> contributions;

	public Wish() { }

	

	public Wish(Profile profile, String name, String description, String url, Double totalValue, Date date,
			List<Contribution> contributions) {
		this.name = name;
		this.profile = profile;
		this.description = description;
		this.url = url;
		this.totalValue = totalValue;
		this.dueDate = date;
		this.contributions = contributions;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<Contribution> getContributions() {
		return contributions;
	}

	public void setContributions(List<Contribution> contributions) {
		this.contributions = contributions;
	}
	
	@PrePersist
	private void onPrePersist() {
		this.creationDate = new Date();
	}
}
