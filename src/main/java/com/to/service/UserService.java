package com.to.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.to.VO.Department;
import com.to.VO.ResponseTemplate;
import com.to.entity.User;
import com.to.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	UserRepo repo;
    @Autowired
    RestTemplate restTemplate;
	public ResponseTemplate findByUserId(Long uid) {
		log.info("Inside UserService findByUserId method");
		ResponseTemplate template=new ResponseTemplate();
		User user=repo.findById(uid).get();
		//getting the department details from the department service
		Department department=restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/"+user.getDepartmentId(),Department.class);
		template.setUser(user);
		template.setDepartment(department);
		return template;
	}

	public List<User> getAllUsers() {
		log.info("Inside UserService getAllUsers method");
		return repo.findAll();
	}

	public User saveUser(User user) {
		log.info("Inside UserService saveUser method");
		return repo.save(user);
	}
}
