package com.hackathon.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hackathon.model.Invitation;
import com.hackathon.model.Profile;

public interface InvitationRepository extends CrudRepository<Invitation, Long> {
	
	List<Invitation> findByProfileTo(Profile profile);
}
