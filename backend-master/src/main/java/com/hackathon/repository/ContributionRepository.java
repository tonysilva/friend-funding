package com.hackathon.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hackathon.model.Contribution;
import com.hackathon.model.Wish;

public interface ContributionRepository extends CrudRepository<Contribution, Long> {

	List<Contribution> findByWish(Wish wish);
	
}
