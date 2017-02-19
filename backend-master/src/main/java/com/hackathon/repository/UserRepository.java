package com.hackathon.repository;

import org.springframework.data.repository.CrudRepository;

import com.hackathon.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);

}
