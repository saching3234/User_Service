package com.to.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.to.VO.ResponseTemplate;
import com.to.entity.User;
import com.to.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired 
	UserService service;
	private  static final String USER_SERVICE="serviceUser";
	
	@GetMapping
	public List<User> getAllUsers(){
		log.info("IN User controller getAllUsers" );
		return service.getAllUsers();		
	}
	
	@GetMapping("/{uid}")
	@CircuitBreaker(name = USER_SERVICE,fallbackMethod = "userFallBack")
	public ResponseEntity< ResponseTemplate> getUser(@PathVariable Long uid) {
		log.info("IN User controller getUser" );
		return  ResponseEntity.ok().body( service.findByUserId(uid));
	}
	
	//fallback method if department service is down
	@GetMapping("/userFallBack")
	public ResponseEntity< String> userFallBack(Exception e) {	
		
		return ResponseEntity.badRequest().body("Department service is down try after some time");
	}
	
	
	@PostMapping
	public User saveUser(@RequestBody User user) {
		log.info("IN User controller saveUser" );

		return service.saveUser(user);
	}	
	
}
