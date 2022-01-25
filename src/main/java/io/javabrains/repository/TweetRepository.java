package io.javabrains.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.javabrains.Tweet;
import io.javabrains.model.User;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
	
	@Query("from Tweet t where t.userid = ?1")
	List<Tweet> findAllByUserId(String userid);
	
}

