package com.sb.springsecurity_demo.config;
import com.sb.springsecurity_demo.service.JwtService;
import com.sb.springsecurity_demo.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /* To validate the token coming in the request, we need to validate three things -
        if the user is present,
        if the token is not tampered and
        if the token is not expired.

        To do this, we will follow these steps:

        1. We will start by extracting the token from the Authorization header of the request. If the header is present
        and starts with 'Bearer ', we will extract the token from it.

        2. Then, we will extract the username from it. If the username is not null and
         SecurityContext does not have authentication, we will validate it with the username we got from database
         and if it is valid, we will set authentication in the SecurityContext.

         */

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);


                //If username is not null and SecurityContext does not have authentication, we can set authentication
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    /* Now, we have token, we can validate it and extract username from it,
             but to validate the token, we need to load user details from database and compare it with the token. */
                    UserDetails userDetails = applicationContext.getBean(MyUserDetailsService.class)
                            .loadUserByUsername(username);

                    if (jwtService.validateToken(token, userDetails)) {

                        /* If the token is valid, we will create an authentication token and set it in the SecurityContext. */
                        /* We will use UsernamePasswordAuthenticationToken for this purpose. */
                        /* We will also set the details of the authentication token using WebAuthenticationDetailsSource. */

                        /* Finally, we will call filterChain.doFilter to pass the request to the next filter in the chain. */
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,      // WHO
                                null,             // no password needed
                                userDetails.getAuthorities() // WHAT they can do
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }


        }
        filterChain.doFilter(request, response);

    }
}
