package com.hackathon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.model.Profile;
import com.hackathon.model.User;
import com.hackathon.repository.ProfileRepository;
import com.hackathon.service.UserService;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@RequestMapping(value="/friends", method=RequestMethod.GET)
	public ResponseEntity<?> getFriends() {
		User user = userService.getAuthenticatedUser();
		return new ResponseEntity<>(user.getProfile().getFriends(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getProfile(@PathVariable("id") Long id) {
		Profile profile = profileRepository.findOne(id);
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> findByName(@RequestParam("param") String param) {
		List<Profile> profiles = profileRepository.findDistinctByNameOrEmailLikeIgnoreCase("%"+param+"%", "%"+param+"%");
		return new ResponseEntity<>(profiles, HttpStatus.OK);
	}
	
	@RequestMapping(value="/me", method=RequestMethod.GET)
	public ResponseEntity<?> getAuthenticatedProfile() {
		User user = userService.getAuthenticatedUser();
		return new ResponseEntity<>(user.getProfile(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{idProfile}/friends", method=RequestMethod.GET)
	public ResponseEntity<?> getFriends(@PathVariable("idProfile") Long idProfile) {
		Profile profileFriend = profileRepository.findOne(idProfile);
		if(profileFriend == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(profileFriend.getFriends(), HttpStatus.OK);
		}
	}
	
}
