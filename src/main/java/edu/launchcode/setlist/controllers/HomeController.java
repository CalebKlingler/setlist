package edu.launchcode.setlist.controllers;


import edu.launchcode.setlist.models.Song;
import edu.launchcode.setlist.models.data.CategoryDao;
import edu.launchcode.setlist.models.data.SetlistDao;
import edu.launchcode.setlist.models.data.SongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping(value = "")
    public String index(Model model){
        model.addAttribute("title", "SETLIST");
        model.addAttribute("songs", songDao.findAll());
        model.addAttribute("setlists", setlistDao.findAll());
        model.addAttribute("categories", categoryDao.findAll());

        return "home/index";
    }
}
