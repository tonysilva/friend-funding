package com.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.model.Invitation;
import com.hackathon.model.Profile;
import com.hackathon.model.User;
import com.hackathon.repository.InvitationRepository;
import com.hackathon.repository.ProfileRepository;
import com.hackathon.service.UserService;

@RestController
@RequestMapping("/invitations")
public class InvitationController {

	@Autowired
	private InvitationRepository invitationRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired 
	private UserService userService;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> sendInvitation(@RequestParam("profileToId") Long profileToId) {
		try {
			User authUser = userService.getAuthenticatedUser();
			Profile profileTo = profileRepository.findOne(profileToId);
			Invitation invitation = new Invitation(profileTo, authUser.getProfile());
			invitationRepository.save(invitation);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> listMyInvitations() {
		try {
			User authUser = userService.getAuthenticatedUser();
			return new ResponseEntity<>(invitationRepository.findByProfileTo(authUser.getProfile()), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/reject/{idInvite}")
	public ResponseEntity<?> rejectInvitation(@PathVariable("idInvite") Long idInvite) {
		try {
			invitationRepository.delete(idInvite);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, path="/accept/{idInvite}")
	public ResponseEntity<?> acceptInvitation(@PathVariable("idInvite") Long idInvite) {
		try {
			Invitation invitation = invitationRepository.findOne(idInvite);
			Profile profileTo = invitation.getProfileTo();
			Profile profileFrom = invitation.getProfileFrom();
			profileTo.getFriends().add(profileFrom);
			profileTo.getFriendsOf().add(profileFrom);
			profileRepository.save(profileTo);
			invitationRepository.delete(idInvite);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
