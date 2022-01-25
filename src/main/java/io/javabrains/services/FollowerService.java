package io.javabrains.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javabrains.model.Follower;
import io.javabrains.repository.FollowerRepository;


@Service
public class FollowerService {
	
	@Autowired
	FollowerRepository followerRepository;
	
	private RedisTemplate<String, String> redisTemplate;
	private HashOperations hashOperations;
	
	// inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations
    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;
    
    @Autowired
	private FanoutService fanoutService;
	
	@Transactional
	public List<Follower> getFollowerDetailsbyUserid(String userid) {
		return followerRepository.findAllByUserId(userid);
	}
	
	@Transactional
	public List<Follower> getAllFollowers() {
		return followerRepository.findAll();
	
	}
	
	@Transactional
	public void addFollower(Follower follower) {
		
		int id = followerRepository.findAll().size() + 1;
		follower.setId(id);
	
		followerRepository.save(follower);
		
		fanoutService.saveFollower(follower);
	
	}
	
	@Transactional
	public void deleteFollower(Follower follower) {			
		followerRepository.deleteById(follower.getId());
	}
	
	public List<String> findAllByUserId(String userid) {
		// TODO Auto-generated method stub
		return listOps.range(userid, 0, -1);
	}
	
	public void deleteFollowers(String userid) {
			 
		 List<Follower> followerList=followerRepository.findAllByUserId(userid);
		 for (int i=0; i<followerList.size();i++) {
			 Follower follower = followerList.get(i);
			 followerRepository.deleteById(follower.getId());
		 }
		 
		 fanoutService.deleteFollowers(userid);
	}
	
	public List<Follower> getAllUsesPag(Integer pageNo, Integer pageSize, String sortBy)
	    {
	      
	        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
	        Page<Follower> pagedResult = followerRepository.findAll(paging);
	         
	        if(pagedResult.hasContent()) {
	            return pagedResult.getContent();
	        } else {
	            return new ArrayList<Follower>();
	        }
	    }
	
	public void unFollowerUser(Follower follower) {
		 
		 Optional<Follower> follower1=followerRepository.findlByUserId(follower.getUserid(),follower.getFollowerUserid());
		  followerRepository.deleteById(follower1.get().getId());
		 
		 
		 fanoutService.unFollowUser(follower);
	}
	 
	
}
