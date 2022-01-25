package io.javabrains;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name="tweet")
public class Tweet implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String userid;
	private String tweet;
	private int likes;
	private int reTweetCount;
	@CreatedDate
	@LastModifiedDate
	private Date createdDate;
	private int replyTweetId;
	private String replyTweetUserid;
	
	public Tweet() {}
	
	public Tweet(int id, String userid, String tweet, int likes, int reTweetCount, Date createdDate, int replyTweetId,
			String replyTweetUserid) {
		super();
		this.id = id;
		this.userid = userid;
		this.tweet = tweet;
		this.likes = likes;
		this.reTweetCount = reTweetCount;
		this.createdDate = createdDate;
		this.replyTweetId = replyTweetId;
		this.replyTweetUserid = replyTweetUserid;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getReTweetCount() {
		return reTweetCount;
	}
	public void setReTweetCount(int reTweetCount) {
		this.reTweetCount = reTweetCount;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getReplyTweetId() {
		return replyTweetId;
	}
	public void setReplyTweetId(int replyTweetId) {
		this.replyTweetId = replyTweetId;
	}
	public String getReplyTweetUserid() {
		return replyTweetUserid;
	}
	public void setReplyTweetUserid(String replyTweetUserid) {
		this.replyTweetUserid = replyTweetUserid;
	}
	
	

	
}
