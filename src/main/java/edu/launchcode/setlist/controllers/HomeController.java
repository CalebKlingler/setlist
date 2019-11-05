package edu.launchcode.setlist.controllers;


import edu.launchcode.setlist.models.Song;
import edu.launchcode.setlist.models.data.SetlistDao;
import edu.launchcode.setlist.models.data.SongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private SetlistDao setlistDao;

    @Autowired
    private SongDao songDao;

    @RequestMapping(value = "")
    public String index(Model model){
        model.addAttribute("Title", "SETLIST");
        model.addAttribute("songs", songDao.findAll());
        model.addAttribute("setlists", setlistDao.findAll());

        return "home/index";
    }
}
