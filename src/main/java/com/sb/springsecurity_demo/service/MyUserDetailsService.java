package com.sb.springsecurity_demo.service;
import com.sb.springsecurity_demo.dao.UserRepo;
import com.sb.springsecurity_demo.model.User;
import com.sb.springsecurity_demo.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    return new UserPrincipal(user);
    }

}
