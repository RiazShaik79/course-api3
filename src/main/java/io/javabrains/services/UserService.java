package io.javabrains.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.javabrains.model.ForgotPasswordRequest;
import io.javabrains.model.OTPModel;
import io.javabrains.model.User;

import io.javabrains.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OTPService otpService; 
	
	@Transactional
	public Optional<User> getUserDetails(String username) {
		return userRepository.findByName(username);
	}
	
	@Transactional
	public List<User> getAllUsers() {
		return userRepository.findAll();
	
	}
	
	@Transactional
	public void addUser(User user) {
		
		Optional<User> user1=userRepository.findByName(user.getUsername());
		int id = userRepository.findAll().size() + 1;
		user.setId(id);
		
		user.setRoles("ROLE_USER");
		user.setActive(true);
		
		if (user1.isPresent()) {
			userRepository.deleteById(user1.get().getId());
		}
			
		userRepository.save(user);
	
	}
	
	@Transactional
	public void updateUser(User user) {
	
		Optional<User> user1=userRepository.findByName(user.getUsername());
		
		user.setId(user.getId());
		
		user.setRoles("ROLE_USER");
		user.setActive(true);
		
		if (user1.isPresent()) {
			userRepository.deleteById(user1.get().getId());
		}
			
		userRepository.save(user);
	
	}
	
	 public List<User> getAllUsesPag(Integer pageNo, Integer pageSize, String sortBy)
	    {
	      
	        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
	        Page<User> pagedResult = userRepository.findAll(paging);
	         
	        if(pagedResult.hasContent()) {
	            return pagedResult.getContent();
	        } else {
	            return new ArrayList<User>();
	        }
	    }
	 
	 
	 public ResponseEntity<?> verifyUserexists(String username) {
			if ( userRepository.findByName(username).isPresent()) {
				
				Optional<User> user = userRepository.findByName(username);
				return otpService.sendOTP(user.get().getPhone())	;
			}
			return null;
			
			}
			
			public ResponseEntity<?> verifyUserOtp(ForgotPasswordRequest forgotPasswordRequest) {
				OTPModel otpModel = new OTPModel();
				otpModel.setMobilenumber(forgotPasswordRequest.getPhone());
				otpModel.setOtp(forgotPasswordRequest.getOtp());
				
				return otpService.verifyOTP(otpModel);
				
			}

			public String resetPassword(ForgotPasswordRequest forgotPasswordRequest) {
				Optional<User> user = userRepository.findByName(forgotPasswordRequest.getUsername());
				
				user.get().setPassword(forgotPasswordRequest.getNpassword());
				user.get().setCpassword(forgotPasswordRequest.getCnpassword());
				userRepository.deleteById(user.get().getId());
				userRepository.save(user.get());
				return "Password Reset Successfully";
			}

}
