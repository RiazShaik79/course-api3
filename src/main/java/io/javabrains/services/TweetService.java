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
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javabrains.Tweet;
import io.javabrains.repository.TweetRepository;


@Service
public class TweetService {
	
	@Autowired
	TweetRepository tweetRepository;
	
	private RedisTemplate<String, Tweet> redisTemplate;
		
	// inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations
    @Resource(name="redisTemplate")
    private ListOperations<String, Tweet> listOps;
    
    @Autowired
	private FanoutService fanoutService;
	
	@Transactional
	public Optional<Tweet> getTweetDetails(int Tweetid) {
		return tweetRepository.findById(Tweetid);
	}
	
	@Transactional
	public List<Tweet> getAllTweets() {
		//return tweetRepository.findAll();
		return listOps.range("ALL TWEETS", 0, -1);
	
	}
	
	public List<Tweet> findAllByUserId(String userid) {
		// TODO Auto-generated method stub
		return listOps.range(userid, 0, -1);
	}
	
	@Transactional
	public void addTweet(Tweet tweet) {
		
		tweetRepository.save(tweet);
		fanoutService.saveTweet(tweet);
	}
	
	@Transactional
	public void updateTweet(Tweet tweet) {
		
		tweetRepository.save(tweet);
		fanoutService.updateTweet(tweet);
	}
	
	@Transactional
	public void reTweet(Tweet tweet,String userid) {
		
		tweet.setUserid(userid);
		tweet.setReTweetCount(tweet.getReTweetCount()+1);
		tweetRepository.save(tweet);
		fanoutService.saveTweet(tweet);
	}
	
	public List<Tweet> getAllTweetsPag(Integer pageNo, Integer pageSize, String sortBy)
	    {
	      
	        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
	        Page<Tweet> pagedResult = tweetRepository.findAll(paging);
	         
	        if(pagedResult.hasContent()) {
	            return pagedResult.getContent();
	        } else {
	            return new ArrayList<Tweet>();
	        }
	    }
	 
	@Transactional
	public void deleteTweets(String userid) {
		
		 List<Tweet> tweetList=tweetRepository.findAllByUserId(userid);
		 for (int i=0; i<tweetList.size();i++) {
			 Tweet tweet = tweetList.get(i);
			 tweetRepository.deleteById(tweet.getId());
		 }
		 
		 fanoutService.deleteTweets(userid);
	}
	
	
	@Transactional
	public void deleteTweet(Tweet tweet) {
			 
		tweetRepository.deleteById(tweet.getId());
		fanoutService.deleteTweet(tweet);
		 }
	
		

}
