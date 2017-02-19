package com.test.pojos;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.hackathon.model.Profile;
import com.hackathon.repository.ProfileRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProfileTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Test
	public void insertTest() {
		Profile profile = new Profile("Joao", "joao@mail.com", null, "/profile/joaoPeba", 123L, null);
		this.entityManager.persistAndFlush(profile);
		assertNotNull(profile.getId());
	}
	
}
