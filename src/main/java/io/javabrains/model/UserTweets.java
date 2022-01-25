package io.javabrains.model;

import java.util.List;

import io.javabrains.Tweet;

public class UserTweets {
	
	private String userid;
	private List<Tweet> tweets;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public UserTweets(String userid, List<Tweet> tweets) {
		super();
		this.userid = userid;
		this.tweets = tweets;
	}

	public UserTweets() {} 
	
	
}
