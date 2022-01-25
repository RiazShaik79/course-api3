package io.javabrains.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.javabrains.model.Follower;
import io.javabrains.model.User;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, Integer> {
	
	@Query("from Follower f where f.userid = ?1")
	List<Follower> findAllByUserId(String userid);
	
	@Query("from Follower f where f.userid = ?1 and f.followerUserid = ?2")
	Optional<Follower> findlByUserId(String userid,String followerUserid);
	
}

