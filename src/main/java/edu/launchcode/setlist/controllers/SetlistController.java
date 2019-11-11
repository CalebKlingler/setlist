package edu.launchcode.setlist.controllers;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import edu.launchcode.setlist.models.Setlist;
import edu.launchcode.setlist.models.Song;
import edu.launchcode.setlist.models.data.SetlistDao;
import edu.launchcode.setlist.models.data.SongDao;
import edu.launchcode.setlist.models.forms.AddSongToSetlistForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
        Iterable<Song> theSongs = songDao.findAll();
        model.addAttribute("songs", theSongs);
        model.addAttribute("setlist", theSetlist);
        model.addAttribute("title", "Add Song to Setlist" + theSetlist.getVenue());
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
        model.addAttribute("setlist", theSetlist);
        model.addAttribute("date", date);
        model.addAttribute("songs", theSetlist.getSongs());
        return "setlist/view";
    }
}