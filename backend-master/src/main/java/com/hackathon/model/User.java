package com.hackathon.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotBlank(message= "{NotBlank.user.username}")
	@Column(unique=true)
	private String username;

	@NotBlank(message= "{NotBlank.user.password}")
	private String password;

	@OneToOne(cascade=CascadeType.ALL, optional=false)
	@NotNull(message="{NotNull.user.profile}")
	private Profile profile;


	public User(String username, String password, Profile profile) {
		super();
		this.username = username;
		this.password = password;
		this.profile = profile;
	}

	public User() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}


}
