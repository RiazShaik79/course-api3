package io.javabrains.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import io.javabrains.model.User;

@Repository
public class UserRepositoryRedisImpl implements UserRepositoryRedis {
	
	private RedisTemplate<String, User> redisTemplate;
	
	private HashOperations hashOperations;

	public UserRepositoryRedisImpl(RedisTemplate<String, User> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		hashOperations.put("USER", user.getId(), user);
		
	}

	@Override
	public Map<String, User> finaAll() {
		// TODO Auto-generated method stub
		return hashOperations.entries("USER");
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return (User)hashOperations.get("USER", id);
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		hashOperations.put("USER", user.getId(), user);
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		hashOperations.delete("USER", id);
		
	}
	
	

}
