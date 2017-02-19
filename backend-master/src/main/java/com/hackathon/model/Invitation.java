package com.hackathon.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Invitation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne(cascade=CascadeType.REFRESH, optional=false)
	@NotNull(message="{NotNull.user.profileTo}")
	private Profile profileTo;
	
	@OneToOne(cascade=CascadeType.REFRESH, optional=false)
	@NotNull(message="{NotNull.user.profileFrom}")
	private Profile profileFrom;

	public Invitation() {
		super();
	}

	public Invitation(Profile profileTo, Profile profileFrom) {
		super();
		this.profileTo = profileTo;
		this.profileFrom = profileFrom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Profile getProfileTo() {
		return profileTo;
	}

	public void setProfileTo(Profile profileTo) {
		this.profileTo = profileTo;
	}

	public Profile getProfileFrom() {
		return profileFrom;
	}

	public void setProfileFrom(Profile profileFrom) {
		this.profileFrom = profileFrom;
	}
	
}
