package com.sb.springsecurity_demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String Hello(HttpServletRequest req) {
        return "Hello, World!" + req.getSession().getId();
    }

    @GetMapping("/about")
    public String About(HttpServletRequest req) {
        return "Spring Security Demo Application"  + req.getSession().getId();
    }




}
