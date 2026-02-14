package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(HttpSession session) {
    if (session.getAttribute("USER_EMAIL") == null) {
        return "redirect:/login";
    }
    return "home";
}

}
