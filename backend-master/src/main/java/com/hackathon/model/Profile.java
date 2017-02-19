package com.hackathon.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Profile {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@NotBlank(message= "{NotBlank.profile.name}")
	private String name;

	@NotBlank(message= "{NotBlank.profile.email}")
	@Column(unique=true)
	@Email
	private String email;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="profile_friends",
			joinColumns=@JoinColumn(name="profile_id"),
			inverseJoinColumns=@JoinColumn(name="friends_id"))
	@JsonIgnore
	private List<Profile> friends;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="profile_friends",
			joinColumns=@JoinColumn(name="friends_id"),
			inverseJoinColumns=@JoinColumn(name="profile_id"))
	@JsonIgnore
	private List<Profile> friendsOf;

	private String url;

	private String profileImg;

	private Long score;

	public Profile() { }

	public Profile(String name, String email, List<Profile> friends, String url, Long score, String profileImg) {
		super();
		this.name = name;
		this.email = email;
		this.friends = friends;
		this.url = url;
		this.score = score;
		this.profileImg = profileImg;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Profile> getFriends() {
		return friends;
	}

	public void setFriends(List<Profile> friends) {
		this.friends = friends;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public boolean isFriend(Profile profile) {
		return friends.contains(profile);
	}

	public List<Profile> getFriendsOf() {
		return friendsOf;
	}

	public void setFriendsOf(List<Profile> friendsOf) {
		this.friendsOf = friendsOf;
	}

}
