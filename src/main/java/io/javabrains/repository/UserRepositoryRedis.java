package io.javabrains.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Repository;

import io.javabrains.model.User;


public interface UserRepositoryRedis {
	
	void save(User user);
	Map<String, User> finaAll();
	User findById(int id);
	void update(User user);
	void delete(String id);
	

}
