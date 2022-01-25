package io.javabrains.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.javabrains.Tweet;
import io.javabrains.model.AuthenticationRequest;
import io.javabrains.model.Follower;
import io.javabrains.model.ForgotPasswordRequest;
import io.javabrains.model.User;
import io.javabrains.model.UserRequest;
import io.javabrains.security.config.jwtUtil;
import io.javabrains.services.FollowerService;
import io.javabrains.services.TweetService;
import io.javabrains.services.UserService;

@RestController
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TweetService tweetService;
	
	@Autowired
	private FollowerService followerService;
	
/*****************User Authentication Start ********************/
	
	@Autowired
	private jwtUtil jwtTokenUtil;

	@Autowired
	private  AuthenticationManager authenticationManager;
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	@CrossOrigin(origins = "http://localhost:3000")
	public String validateLogin(@RequestBody AuthenticationRequest authenticationRequest) {
		System.out.println(authenticationRequest.getEmail() + " " + authenticationRequest.getPassword());
		
		Authentication authentication;
		try{
			 authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), 
					authenticationRequest.getPassword())
				);
 
			} catch(BadCredentialsException e) {
			System.out.println("error : " + e);
			return e.getMessage();
		}
		
		return jwtTokenUtil.generateToken(authenticationRequest.getEmail());
		
	} 
	
	/*****************User Controller Start ********************/
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/users")
	@CrossOrigin(origins = "http://localhost:3000")
	public void addUser(@RequestBody User user) {
		
		userService.addUser(user);		
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.PUT, value="/users")
	@CrossOrigin(origins = "http://localhost:3000")
	public void updateUser(@RequestBody User user) {
		
		userService.updateUser(user);		
	}
	
	@RequestMapping("/users")
	@CrossOrigin(origins = "http://localhost:3000")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping("/userspag/{pageNo}/{pageSize}/{sortBy}")
	@CrossOrigin(origins = "http://localhost:3000")
	public List<User> getAllUsesPag(@PathVariable Integer pageNo, @PathVariable Integer pageSize, @PathVariable String sortBy) {
		System.out.println("variables " + pageNo + " " + pageSize + " " + sortBy);
		return userService.getAllUsesPag(pageNo, pageSize, sortBy);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/users/get")
	@CrossOrigin(origins = "http://localhost:3000")
	public Optional<User> getUserDetails(@RequestBody UserRequest userRequest) {
	
		return userService.getUserDetails(userRequest.getUsername());
	}
	
		
	@RequestMapping(method=RequestMethod.POST, value="/verifyuser")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> verifyUserExists(@RequestBody ForgotPasswordRequest forgetPasswordRequest) {
	return userService.verifyUserexists(forgetPasswordRequest.getUsername());
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/verifyotp")
	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> verifyUserOtp(@RequestBody ForgotPasswordRequest forgetPasswordRequest) {
	return userService.verifyUserOtp(forgetPasswordRequest);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/resetpassword")
	@CrossOrigin(origins = "http://localhost:3000")
	public String resetPassword(@RequestBody ForgotPasswordRequest forgetPasswordRequest) {
	return userService.resetPassword(forgetPasswordRequest);
	}
	
	/*****************User Controller End ********************/
	
	/*****************Tweets Controller Start ********************/
	
	@RequestMapping("/tweets/{userid}")
	@CrossOrigin(origins = "http://localhost:3000")
	public List<Tweet> getAllTweetsByUserid(@PathVariable String userid) {
			return tweetService.findAllByUserId(userid+"t");
	}
	
	@Transactional
	@RequestMapping(value="/tweets")
	@CrossOrigin(origins = "http://localhost:3000")
	public List<Tweet> getAllTweets() {
			return tweetService.getAllTweets();
		
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/tweets")
	@CrossOrigin(origins = "http://localhost:3000")
	public void addTweet(@RequestBody Tweet tweet) {
		
		tweetService.addTweet(tweet);		
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.PUT, value="/tweets")  //like tweet
	@CrossOrigin(origins = "http://localhost:3000")
	public void updateTweet(@RequestBody Tweet tweet) {
		
		tweetService.updateTweet(tweet);		
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/tweets/{userid}")
	@CrossOrigin(origins = "http://localhost:3000")
	public void reTweet(@RequestBody Tweet tweet, @PathVariable String userid) {
		
		tweetService.reTweet(tweet,userid);		
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.DELETE, value="/tweets/{userid}")
	@CrossOrigin(origins = "http://localhost:3000")
	public void deleteTweets(@PathVariable String userid) {
		
		tweetService.deleteTweets(userid);
		
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.DELETE, value="/tweets")
	@CrossOrigin(origins = "http://localhost:3000")
	public void deleteTweet(@RequestBody Tweet tweet) {
		
		tweetService.deleteTweet(tweet);
		
	}
	
	/*****************Tweets Controller End ********************/
	
	
	/*****************Followers Controller Start ********************/
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST, value="/followers")
	@CrossOrigin(origins = "http://localhost:3000")
	public void addFollower(@RequestBody Follower follower) {
		
		followerService.addFollower(follower);
		
	}
		
	@RequestMapping("/followers/{userid}")
	@CrossOrigin(origins = "http://localhost:3000")
	public List<String> getAllFollowerbyUserid(@PathVariable String userid) {
		return followerService.findAllByUserId(userid+"f");
	}
	
	@RequestMapping("/following/{userid}")
	@CrossOrigin(origins = "http://localhost:3000")
	public List<String> getAllFollowingbyUserid(@PathVariable String userid) {
		return followerService.findAllByUserId(userid+"fo");
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.DELETE, value="/followers")
	@CrossOrigin(origins = "http://localhost:3000")
	public void unFollowFollower(@RequestBody Follower follower) {
		
		followerService.unFollowerUser(follower);
		
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.DELETE, value="/followers/{userid}")
	@CrossOrigin(origins = "http://localhost:3000")
	public void deleteFollowers(@PathVariable String userid) {
		
		followerService.deleteFollowers(userid);
		
	}
	
	/*****************Followers Controller End ********************/
	
}
