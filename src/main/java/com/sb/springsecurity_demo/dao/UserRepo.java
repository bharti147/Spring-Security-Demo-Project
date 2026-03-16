package com.sb.springsecurity_demo.dao;

import com.sb.springsecurity_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
   User findByUsername(String username);
}
