package io.javabrains.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import javax.persistence.Id;


@Entity
@Table(name="Follower")
public class Follower {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String userid;
	private String followerUserid;
	
	public String getFollowerUserid() {
		return followerUserid;
	}


	public void setFollowerUserid(String followerUserid) {
		this.followerUserid = followerUserid;
	}


	public Follower(int id, String userid, String followerUserid) {
		super();
		this.id = id;
		this.userid = userid;
		this.followerUserid = followerUserid;
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

	public Follower() {} 
	
	@Override
    public String toString() {
        return String.format("User{id=%d, userid='%s', followerUserid='%s'}",
        		id, userid, followerUserid) ;
    }
	
}
