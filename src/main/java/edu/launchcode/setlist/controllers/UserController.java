package edu.launchcode.setlist.controllers;

import edu.launchcode.setlist.models.User;

import edu.launchcode.setlist.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "add", method=RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("title", "Create User");
        model.addAttribute(new User());
        return "user/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddUserForm(@ModelAttribute @Valid User newUser, Errors errors, Model model){
        userDao.save(newUser);
        String message = "Welcome " + newUser.getUsername() + "!";
        model.addAttribute("message", message);
        return "user/welcome";
    }
}
