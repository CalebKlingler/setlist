package edu.launchcode.setlist.controllers;

import edu.launchcode.setlist.models.Songlist;
import edu.launchcode.setlist.models.data.SonglistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("songlist")
public class SonglistController {

    @Autowired
    private SonglistDao songlistDao;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("title", "Add Songlist");
        model.addAttribute(new Songlist());
        return "songlist/add";
    }
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddSonglist(@ModelAttribute @Valid Songlist newSonglist, Errors errors, Model model){
        songlistDao.save(newSonglist);
        model.addAttribute("songlist", newSonglist);
        model.addAttribute("title", newSonglist.getName());
        return "songlist/index";
    }
}



