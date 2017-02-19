package com.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hackathon.model.Contribution;
import com.hackathon.model.Profile;
import com.hackathon.model.User;
import com.hackathon.model.Wish;
import com.hackathon.repository.ProfileRepository;
import com.hackathon.repository.WishRepository;

@Component
public class WishService {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WishRepository wishRepository;
	

	public boolean canSeeWishes(Long profileId) {
		Profile profile = profileRepository.findOne(profileId);
		User user = userService.getAuthenticatedUser();
		
		if (!user.getProfile().isFriend(profile) && user.getProfile().getId() != profile.getId()) {
			return false;
		}
		
		return true;
	}
	
	public Double getTotalContributed(Long id) {
		Wish wish = wishRepository.findOne(id);
		Double total = new Double(0.0);
		for (Contribution contribution : wish.getContributions()) {
			total += contribution.getValue();
		}
		return total;
	}
	
}
