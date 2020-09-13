package com.jonny.chat.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @RequestMapping(value = {"", "/", "/main"})
    public String index(HttpSession session, Authentication auth) {
        session.setAttribute("userId", auth.getName());
        return "index";
    }

    @RequestMapping("/auth")
    public String login(Boolean error, Model model) {
        model.addAttribute("error", error);
        return "auth";
    }
}
