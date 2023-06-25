package com.to.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.to.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

}
