package edu.launchcode.setlist.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("logout")
public class LogoutController {
    @RequestMapping(value = "")
    public String logout(){
        SecurityContextHolder.clearContext();
        return "login/login";
    }

}
