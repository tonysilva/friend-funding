package com.hackathon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hackathon.model.Profile;
import com.hackathon.model.Wish;
import com.hackathon.repository.WishRepository;

@Component
public class ProfileService {
	
	@Autowired
	private WishRepository wishRepository;
	
	public List<Wish> getFriendWishes(Profile profile) {
		List<Wish> wishes = new ArrayList<Wish>();
		for (Profile friend : profile.getFriends()) {
			wishes.addAll(wishRepository.findByProfile(friend));
		}
		return wishes.size() > 0 ? wishes : null;
	}
	
}
