package edu.launchcode.setlist.controllers;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import edu.launchcode.setlist.models.Setlist;
import edu.launchcode.setlist.models.Song;
import edu.launchcode.setlist.models.data.SetlistDao;
import edu.launchcode.setlist.models.data.SongDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("setlist")
public class SetlistController {

    @Autowired
    private SetlistDao setlistDao;
    @Autowired
    private SongDao songDao;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addSetlist(Model model){
        model.addAttribute("title", "Add Setlist");
        model.addAttribute(new Setlist());
        return "setlist/add";

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddSetlist(@ModelAttribute @Valid Setlist newSetlist, Errors errors,  Model model){
        setlistDao.save(newSetlist);
        model.addAttribute("setlist", newSetlist);
        String date = newSetlist.getMonth() + "/" + newSetlist.getDay() + "/" + newSetlist.getYear();
        model.addAttribute("date", date);
        return "setlist/index";
    }

    @RequestMapping(value="edit/{setlistId}", method = RequestMethod.GET)
    public String editSetlist(Model model, @PathVariable int setlistId){
        Setlist theSetlist = setlistDao.findById(setlistId).get();
        List<Song> theSetlistSongs = theSetlist.getSongs();
        Iterable<Song> allSongs = songDao.findAll();
        List<Song> theSongs = new ArrayList<>();
        for (Song song : allSongs){
            if (theSetlistSongs.contains(song)){
                continue;
            }
            else{
                theSongs.add(song);
            }
        }
        model.addAttribute("songs", theSongs);
        model.addAttribute("setlistSongs", theSetlistSongs);
        model.addAttribute("setlist", theSetlist);
        model.addAttribute("title", "Add Song to Setlist " + theSetlist.getVenue());
        return "setlist/addSongs";

    }

    @RequestMapping(value = "edit/{setlistId}", method = RequestMethod.POST)
    public String processEditSetlist(Model model,
                                     @PathVariable int setlistId,
                                     @RequestParam(value = "songs", required = false)int[] songs){
        Setlist theSetlist = setlistDao.findById(setlistId).get();
        for(int songId : songs){
            theSetlist.addSong(songDao.findById(songId).get());
        }
        setlistDao.save(theSetlist);
        String date = theSetlist.getMonth() + "/" + theSetlist.getDay() + "/" + theSetlist.getYear();
        String totalTime = theSetlist.getTotalTime();
        List<Song> allSongs = theSetlist.getSongs();
        model.addAttribute("setlist", theSetlist);
        model.addAttribute("date", date);
        model.addAttribute("songs", theSetlist.getSongs());
        model.addAttribute("time", totalTime );

        return "setlist/view";
    }
    @RequestMapping(value = "view/{setlistId}", method = RequestMethod.GET)
    public String viewSetlist(Model model, @PathVariable int setlistId){
        Setlist theSetlist = setlistDao.findById(setlistId).get();
        String date = theSetlist.getMonth() + "/" + theSetlist.getDay() + "/" + theSetlist.getYear();
        String totalTime = theSetlist.getTotalTime();
        List<Song> allSongs = theSetlist.getSongs();
        model.addAttribute("setlist", theSetlist);
        model.addAttribute("date", date);
        model.addAttribute("songs", theSetlist.getSongs());
        model.addAttribute("time", totalTime);
        return "setlist/view";
    }
    @RequestMapping(value = "delete/{setlistId}", method = RequestMethod.GET)
    public String deleteSetlist(Model model, @PathVariable int setlistId){
        Setlist theSetlist = setlistDao.findById(setlistId).get();
        setlistDao.delete(theSetlist);
        model.addAttribute("message", theSetlist.getVenue() + " has been successfully deleted!");
        return "setlist/delete";
    }
}


