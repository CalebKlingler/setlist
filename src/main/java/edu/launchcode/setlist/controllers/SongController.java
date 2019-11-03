package edu.launchcode.setlist.controllers;

import edu.launchcode.setlist.models.Song;
import edu.launchcode.setlist.models.data.SongDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;




@Controller
@RequestMapping("song")
public class SongController {

    @Autowired
    private SongDao songDao;
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addSong(Model model){
        model.addAttribute("title" , "Add Song");
        model.addAttribute(new Song());
        return "song/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddSong(@ModelAttribute @Valid Song newSong, Errors errors, Model model){
        songDao.save(newSong);
        model.addAttribute("song", newSong);
        model.addAttribute("title", "Your new song!");
        return "song/index";
    }
}
