package com.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.model.Contribution;
import com.hackathon.model.User;
import com.hackathon.model.Wish;
import com.hackathon.repository.ContributionRepository;
import com.hackathon.repository.WishRepository;
import com.hackathon.service.UserService;

@RestController
@RequestMapping("/contributions")
public class ContributionController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WishRepository wishRepository;
	
	@Autowired
	private ContributionRepository contributionRepository;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> addContribution(@Validated @RequestBody Contribution contribution, @RequestParam("wishid") Long wishId) {
		User authUser = userService.getAuthenticatedUser();
		contribution.setContributor(authUser.getProfile());
		
		Wish wish = wishRepository.findOne(wishId);
		if (wish == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		contribution.setWish(wish);
		return new ResponseEntity<>(contributionRepository.save(contribution), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{wishId}", method=RequestMethod.GET)
	public ResponseEntity<?> getContributions(@PathVariable("wishId") Long wishId) {
		User user = userService.getAuthenticatedUser();
		Wish wish = wishRepository.findOne(wishId);
		if (wish == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (!user.getProfile().isFriend(wish.getProfile()) && user.getProfile().getId() != wish.getProfile().getId()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>(contributionRepository.findByWish(wish), HttpStatus.OK);
		
	}
}
