package com.hackathon.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hackathon.model.Profile;
import com.hackathon.model.Wish;

public interface WishRepository extends CrudRepository<Wish, Long> {

	List<Wish> findByProfile(Profile profile);
	
}
