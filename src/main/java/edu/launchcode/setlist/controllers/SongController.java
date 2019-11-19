package edu.launchcode.setlist.controllers;

import edu.launchcode.setlist.models.Song;
import edu.launchcode.setlist.models.data.SongDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.swing.text.EditorKit;
import javax.validation.Valid;
import java.util.List;


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
        String totalTime = newSong.getTotalTime();
        model.addAttribute("song", newSong);
        model.addAttribute("time", totalTime);
        model.addAttribute("title", "Your new song!");
        return "song/index";
    }
    @RequestMapping(value = "edit/{songId}", method = RequestMethod.GET)
    public String editSong(Model model, @PathVariable int songId){
        Song theSong = songDao.findById(songId).get();
        model.addAttribute("song", theSong);
        model.addAttribute("title", "Edit Song");
        return "song/edit";

    }
    @RequestMapping(value = "edit/{songId}", method = RequestMethod.POST)
    public String processEditSong(Model model, @PathVariable int songId, @Valid Song newSong){
        Song theSong = songDao.findById(songId).get();
        theSong.setArtist(newSong.getArtist());
        theSong.setName(newSong.getName());
        theSong.setMinutes(newSong.getMinutes());
        theSong.setSeconds(newSong.getSeconds());
        songDao.save(theSong);
        String totalTime = theSong.getTotalTime();
        model.addAttribute("song", theSong);
        model.addAttribute("time", totalTime);
        model.addAttribute("title", "Updated song!");
        return "song/index";

    }

    @RequestMapping(value = "view/{songId}", method = RequestMethod.GET)
    public String viewSong(Model model, @PathVariable int songId){
        Song theSong = songDao.findById(songId).get();
        String totalTime = theSong.getTotalTime();
        model.addAttribute("song", theSong);
        model.addAttribute("title", theSong.getName());
        model.addAttribute("time", totalTime);
        return "song/view";

    }
    @RequestMapping(value = "delete/{songId}", method = RequestMethod.GET)
    public String deleteSong (Model model, @PathVariable int songId){
        Song theSong = songDao.findById(songId).get();
        songDao.delete(theSong);
        model.addAttribute("message", theSong.getName() + " has been successfully deleted!");
        return "song/delete";
    }
}
