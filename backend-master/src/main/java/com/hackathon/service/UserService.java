package com.hackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.hackathon.error.handler.Message;
import com.hackathon.model.Profile;
import com.hackathon.model.User;
import com.hackathon.repository.ProfileRepository;
import com.hackathon.repository.UserRepository;

@Component
public class UserService {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfileRepository profileRepository;

	public Message validateUserCreation(User user) {
		Message msg = new Message();
		if (userRepository.findByUsername(user.getUsername()) != null) {
			msg.addError(messageSource.getMessage("Unique.user.username", null, LocaleContextHolder.getLocale()));
		}

		if (profileRepository.findByEmail(user.getProfile().getEmail()) != null) {
			msg.addError(messageSource.getMessage("Unique.profile.email", null, LocaleContextHolder.getLocale()));
		}
		return msg;
	}

	public Message validateUserUpdate(User user) {
		Message msg = new Message();

		User userAux = userRepository.findByUsername(user.getUsername());
		if (userAux != null && userAux.getId() != user.getId()) {
			msg.addError(messageSource.getMessage("Unique.user.username", null, LocaleContextHolder.getLocale()));
		}

		Profile profileAux = profileRepository.findByEmail(user.getProfile().getEmail());
		if (profileAux != null && profileAux.getId() != user.getProfile().getId()) {
			msg.addError(messageSource.getMessage("Unique.profile.email", null, LocaleContextHolder.getLocale()));
		}
		return msg;
	}

	public boolean userExists(User user) {
		if (user.getId() == null || userRepository.findOne(user.getId()) == null) {
			return false;
		}
		return true;
	}

	public User getAuthenticatedUser() {
		Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(authUser.getName());
		return user;
	}
}
