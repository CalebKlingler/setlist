package edu.launchcode.setlist.controllers;

import com.sun.org.apache.bcel.internal.generic.NEW;
import edu.launchcode.setlist.models.Library;
import edu.launchcode.setlist.models.User;
import edu.launchcode.setlist.models.data.LibraryDao;
import edu.launchcode.setlist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("library")
public class LibraryController {
    @Autowired
    private UserService userService;

    @Autowired
    private LibraryDao libraryDao;
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String library(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Library theLibrary = new Library();

        libraryDao.save(theLibrary);
        model.addAttribute("welcomeMessage", "Welcome " + user.getName() + " " + user.getLastName() + "! (" + user.getEmail() + ")");
        model.addAttribute("adminMessage","Content Available Only for Users with Admin Role");
        model.addAttribute("title", "SETLIST");
        model.addAttribute("songs", theLibrary.getSongs());
        model.addAttribute("setlists", theLibrary.getSetlists());
        model.addAttribute("categories", theLibrary.getCategories());
        return "redirect:/home";

    }
}
