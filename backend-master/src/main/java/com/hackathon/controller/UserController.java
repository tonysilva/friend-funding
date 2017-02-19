package com.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.error.handler.Message;
import com.hackathon.model.Profile;
import com.hackathon.model.User;
import com.hackathon.repository.ProfileRepository;
import com.hackathon.repository.UserRepository;
import com.hackathon.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired 
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> createUser(@Validated @RequestBody User user) {
		Message msg = new Message();
		msg = userService.validateUserCreation(user);
		if (msg.getErrors() != null) {
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return new ResponseEntity<>(user.getProfile(), HttpStatus.CREATED);
	}

	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@Validated @RequestBody Profile profile) {
		Message msg = new Message();

		User authUser = userService.getAuthenticatedUser();
		if (authUser.getProfile().getId() != profile.getId()) {
			msg.addError(messageSource.getMessage("Authentication.error", null, LocaleContextHolder.getLocale()));
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}

		if (msg.getErrors() != null) {
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(profileRepository.save(profile), HttpStatus.OK);
	}

	@RequestMapping(method=RequestMethod.PUT, path="/{id}")
	public ResponseEntity<?> addFriend(@RequestBody Profile profileFrom, @PathVariable String idProfileAdd) {
		Message msg = new Message();
		try {
			Profile profileTo = profileRepository.findOne(new Long(idProfileAdd));
			profileFrom.getFriends().add(profileTo);
			profileTo.getFriends().add(profileFrom);
			profileRepository.save(profileFrom);
			profileRepository.save(profileTo);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
	}
}
