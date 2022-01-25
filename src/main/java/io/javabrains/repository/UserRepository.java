package io.javabrains.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import io.javabrains.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
	
	@Query("{'userid' :?0}")
	Optional<User> findByUserId(String userid);
	
	@Query("{'username':?0}")
	Optional<User> findByName(String username);
	
}

