package edu.launchcode.setlist.controllers;


import edu.launchcode.setlist.models.Library;
import edu.launchcode.setlist.models.Song;
import edu.launchcode.setlist.models.User;
import edu.launchcode.setlist.models.data.CategoryDao;
import edu.launchcode.setlist.models.data.LibraryDao;
import edu.launchcode.setlist.models.data.SetlistDao;
import edu.launchcode.setlist.models.data.SongDao;
import edu.launchcode.setlist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("home")
public class HomeController {
    @Autowired
    private SetlistDao setlistDao;

    @Autowired
    private SongDao songDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private LibraryDao libraryDao;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "")
    public String index(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        Library theLibrary = user.getLibrary();
        model.addAttribute("title", "SETLIST");
        model.addAttribute("songs", theLibrary.getSongs());
        model.addAttribute("setlists", theLibrary.getSetlists());
        model.addAttribute("categories", theLibrary.getCategories());

        return "home/index";
    }
}
