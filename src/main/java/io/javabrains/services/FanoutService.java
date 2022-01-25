package io.javabrains.services;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javabrains.Tweet;
import io.javabrains.model.Follower;




@Service
public class FanoutService {
	
	private RedisTemplate<String, String> redisTemplate;
		
	// inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations
    @Resource(name="redisTemplate")
    private ListOperations<String, Object> listOps;
    
    @Autowired
    FollowerService followerService;
    
    @Autowired
    TweetService tweetService;

	public FanoutService(RedisTemplate<String, String> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	@Transactional
	public void saveTweet(Tweet tweet) {
		// add user tweet to the user list
		listOps.leftPush(tweet.getUserid()+"t", tweet);
		
		// add user tweet to the user followers list
		List<String> followerList = followerService.findAllByUserId(tweet.getUserid()+"fo");
		for (int i=0; i<followerList.size();i++) {
			String userid = followerList.get(i);
			listOps.leftPush(userid+"t", tweet);
		}
		
		// add all user tweets to the list
		listOps.leftPush("ALL TWEETS", tweet);
		
		// if user tweet is a reply to another tweet then save the tweet in the original user reply tweet list
		if (!tweet.getReplyTweetUserid().isEmpty()) {
			listOps.leftPush(tweet.getReplyTweetUserid() +"rt", tweet);
		}
		
	}
	
	@Transactional
	public void updateTweet(Tweet tweet) {
		// Update user tweet to the user list
		List<Tweet> tweetList = tweetService.findAllByUserId(tweet.getUserid()+"t");
		for (int i=0; i<tweetList.size();i++) {
			Tweet tweet1 = tweetList.get(i);
			if (tweet1.getId() == tweet.getId()) {
				tweet.setLikes(tweet.getLikes()+1);
				listOps.remove(tweet.getUserid()+"t", 1, tweet1);
				listOps.leftPush(tweet.getUserid()+"t", tweet);
				break;
			}
		}
		
		// Update tweet to the original user tweeted, his reply tweet list
		tweetList = tweetService.findAllByUserId(tweet.getReplyTweetUserid()+"t");
		for (int i=0; i<tweetList.size();i++) {
			Tweet tweet1 = tweetList.get(i);
			if (tweet1.getId() == tweet.getId()) {
				listOps.remove(tweet.getReplyTweetUserid()+"rt", 1, tweet1);
				listOps.leftPush(tweet.getReplyTweetUserid()+"rt", tweet);
				break;
			}
		}
		
		// update user tweet to the all Tweets user list
		List<Tweet> allTweetList = tweetService.findAllByUserId("ALL TWEETS");
		for (int i=0; i<allTweetList.size();i++) {
			Tweet tweet1 = allTweetList.get(i);
			if (tweet1.getId() == tweet.getId()) {
				listOps.remove("ALL TWEETS", 1, tweet1);
				listOps.leftPush("ALL TWEETS", tweet);
				break;
			}
		}
		
		// update user tweet to the user followers list
		List<String> followerList = followerService.findAllByUserId(tweet.getUserid()+"fo");
		for (int i=0; i<followerList.size();i++) {
			String userid = followerList.get(i);
			tweetList = tweetService.findAllByUserId(userid);
			
			for (int j=0; i<tweetList.size();j++) {
				Tweet tweet1 = allTweetList.get(j);
				if (tweet1.getId() == tweet.getId()) {
					listOps.remove(userid, 1, tweet1);
					listOps.leftPush(userid, tweet);
					break;
				}
			}
		}
		
	}
	
	@Transactional
	public void saveFollower(Follower follower) {
		// add user following to the user list
		listOps.leftPush(follower.getUserid()+"f", follower.getFollowerUserid());
		
		// add user follower to the main user list
		listOps.leftPush(follower.getFollowerUserid()+"fo", follower.getUserid());
		
		// add all tweets of the following user to the follower user list Except the same user tweets
		List<Tweet> tweetList = tweetService.findAllByUserId(follower.getFollowerUserid()+"t");
		for (int i=0; i<tweetList.size();i++) {
			Tweet tweet = tweetList.get(i);
			
			if (!tweet.getUserid().equals(follower.getUserid())) {
				listOps.leftPush(follower.getUserid()+"t", tweet);
			}
		}
	}
	
	@Transactional
	public void deleteTweets(String userid) {			 
		// delete all tweets for the user tweets list
		listOps.getOperations().delete(userid+"t");
		
		// delete all tweets for the ALL Tweet tweets list
		 listOps.getOperations().delete("ALL TWEETS");
		  
		  // delete all tweets of the user list who are following tweets of the main user who deleted his tweet
		   List<String> followerList = followerService.findAllByUserId(userid+"fo");
		   
			for (int i=0; i<followerList.size();i++) {
			
				List<Tweet> tweetsList = tweetService.findAllByUserId(followerList.get(i)+"t");
				
						for (int j=0;j<tweetsList.size();j++) {
							Tweet  tweet = tweetsList.get(j);
							if (tweet.getUserid().equals(userid)) {
								listOps.remove(followerList.get(i)+"t", 1, tweet);
							}
						}
					} 
		}
	
	@Transactional
	public void deleteTweet(Tweet tweet) {
		
		// delete tweet for the user tweet list
		 listOps.remove(tweet.getUserid()+"t", 1, tweet);
		 
		 // delete tweet from all tweets list
		 listOps.remove("ALL TWEETS", 1, tweet);
		 
		 // delete tweet of the user list who are following tweets of the main user who deleted his tweet
		   List<String> followerList = followerService.findAllByUserId(tweet.getUserid()+"fo");
		   
			for (int i=0; i<followerList.size();i++) {
					listOps.remove(followerList.get(i)+"t", 1, tweet);
					} 
		
		 }
	
	@Transactional
	public void unFollowUser(Follower follower) {
		
		// delete following user from the user list
		listOps.remove(follower.getUserid()+"f", 1, follower.getFollowerUserid());
		
		// delete follower user from the following user list
		listOps.remove(follower.getFollowerUserid()+"fo", 1, follower.getUserid());
		
		// delete all the following user tweets from the user list
		List<Tweet> tweetsList = tweetService.findAllByUserId(follower.getUserid()+"t");
		
		for (int i=0;i<tweetsList.size();i++) {
			Tweet  tweet = tweetsList.get(i);
			if (tweet.getUserid().equals(follower.getFollowerUserid())) {
				listOps.remove(follower.getUserid()+"t", 1, tweet);
			}
		}
	}
	
	@Transactional
	public void deleteFollowers(String userid) {
		
		// delete following users from the user list
		 listOps.getOperations().delete(userid+"f");
		 
		// delete follower user from the following list
		 listOps.getOperations().delete(userid+"fo");
	}
	

	
}
