package com.sb.springsecurity_demo.dao;

import com.sb.springsecurity_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
   User findByUsername(String username);
}
