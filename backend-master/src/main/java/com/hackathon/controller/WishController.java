package com.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.model.Profile;
import com.hackathon.model.User;
import com.hackathon.model.Wish;
import com.hackathon.repository.ProfileRepository;
import com.hackathon.repository.WishRepository;
import com.hackathon.service.ProfileService;
import com.hackathon.service.UserService;
import com.hackathon.service.WishService;

@RestController
public class WishController {

	@Autowired
	private WishRepository wishRepository;
	
	@Autowired
	private WishService wishService;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path="/wishes/{wish}", method=RequestMethod.GET)
	public ResponseEntity<?> getWish(@PathVariable("wish") Long wishId) {
		User user = userService.getAuthenticatedUser();
		Wish wish = wishRepository.findOne(wishId);
		if (wish == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (!user.getProfile().isFriend(wish.getProfile()) && user.getProfile().getId() != wish.getProfile().getId()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(wish, HttpStatus.OK);
	}
	
	@RequestMapping(value="/wishes/{wish}/donated", method=RequestMethod.GET)
	public ResponseEntity<?> getTotalDonated(@PathVariable("wish") Long wishId) {
		return new ResponseEntity<>(wishService.getTotalContributed(wishId), HttpStatus.OK);
	}
	
	@RequestMapping(value="/wishes", method=RequestMethod.GET)
	public ResponseEntity<?> getFriendWishes() {
		User authUser = userService.getAuthenticatedUser();
		return new ResponseEntity<>(profileService.getFriendWishes(authUser.getProfile()), HttpStatus.OK);  
	}
	
	@RequestMapping(value="/wishes/{profileId}/friends", method=RequestMethod.GET)
	public ResponseEntity<?> getFriendWishesProfile(@PathVariable("profileId") Long profileId) {
		Profile profile = profileRepository.findOne(profileId);
		return new ResponseEntity<>(profileService.getFriendWishes(profile), HttpStatus.OK);  
	}
	
	@RequestMapping(value="/wishes/mine", method=RequestMethod.GET)
	public ResponseEntity<?> getMyWishes() {
		User authUser = userService.getAuthenticatedUser();
		return new ResponseEntity<>(wishRepository.findByProfile(authUser.getProfile()), HttpStatus.OK);  
	}
	
	@RequestMapping(value="/{profileId}/wishes", method=RequestMethod.GET)
	public ResponseEntity<?> getProfileWishes(@PathVariable("profileId") Long profileId) {
		if (!wishService.canSeeWishes(profileId)) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		Profile profile = profileRepository.findOne(profileId);
		return new ResponseEntity<>(wishRepository.findByProfile(profile), HttpStatus.OK);
	}
	
	@RequestMapping(value="/wishes", method={RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity<?> saveWish(@Validated @RequestBody Wish wish) {
		User authUser = userService.getAuthenticatedUser();
		wish.setProfile(authUser.getProfile());
		return new ResponseEntity<>(wishRepository.save(wish), HttpStatus.OK);
	}
	
	@RequestMapping(value="/wishes/{idWish}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteWish(@PathVariable Long idWish) {
		try {
			wishRepository.delete(idWish);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
