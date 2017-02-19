package com.hackathon.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

@Entity
public class Contribution {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional=false)
	private Wish wish;

	@ManyToOne(optional=false)
	private Profile contributor;

	@NotNull(message="NotNull.contribution.value")
	private Double value;

	private Date date;

	public Contribution() { }

	public Contribution(Wish wish, Profile contributor, Double value, Date date) {
		super();
		this.wish = wish;
		this.contributor = contributor;
		this.value = value;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Wish getWish() {
		return wish;
	}

	public void setWish(Wish wish) {
		this.wish = wish;
	}

	public Profile getContributor() {
		return contributor;
	}

	public void setContributor(Profile contributor) {
		this.contributor = contributor;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@PrePersist
	private void onPrePersist() {
		this.date = new Date();
	}

}
