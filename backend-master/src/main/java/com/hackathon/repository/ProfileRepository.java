package com.hackathon.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hackathon.model.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
	
	Profile findByEmail(String email);
	
	List<Profile> findByName(String name);

	List<Profile> findDistinctByNameOrEmailLikeIgnoreCase(String param, String param2);
}
