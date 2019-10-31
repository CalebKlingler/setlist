package edu.launchcode.setlist.controllers;

import edu.launchcode.setlist.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("user")
public class UserController {
    @RequestMapping(value = "add", method=RequestMethod.GET);
    public String add(Model model){
        model.addAttribute("title", "Create User");
        model.addAttribute(new User());
    }
}
