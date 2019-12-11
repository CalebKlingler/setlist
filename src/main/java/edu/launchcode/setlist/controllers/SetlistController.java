package edu.launchcode.setlist.controllers;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import edu.launchcode.setlist.models.Library;
import edu.launchcode.setlist.models.Setlist;
import edu.launchcode.setlist.models.Song;
import edu.launchcode.setlist.models.User;
import edu.launchcode.setlist.models.data.LibraryDao;
import edu.launchcode.setlist.models.data.SetlistDao;
import edu.launchcode.setlist.models.data.SongDao;
import edu.launchcode.setlist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import javax.persistence.Index;
import javax.validation.Valid;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("setlist")
public class SetlistController {

    @Autowired
    private SetlistDao setlistDao;
    @Autowired
    private SongDao songDao;

    @Autowired
    private UserService userService;

    @Autowired
    private LibraryDao libraryDao;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addSetlist(Model model){
        model.addAttribute("title", "Add Setlist");
        model.addAttribute(new Setlist());
        return "setlist/add";

    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddSetlist(@ModelAttribute @Valid Setlist newSetlist, Errors errors,  Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        setlistDao.save(newSetlist);
        Library library = user.getLibrary();
        List<Setlist> setlists = library.getSetlists();
        setlists.add(newSetlist);
        library.setSetlists(setlists);
        libraryDao.save(library);
        model.addAttribute("setlist", newSetlist);
        String date = newSetlist.getMonth() + "/" + newSetlist.getDay() + "/" + newSetlist.getYear();
        model.addAttribute("date", date);
        return "setlist/index";
    }

    @RequestMapping(value="edit/{setlistId}", method = RequestMethod.GET)
    public String editSetlist(Model model, @PathVariable int setlistId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Setlist theSetlist = setlistDao.findById(setlistId).get();
        List<Song> theSetlistSongs = theSetlist.getSongs();
        Library library = user.getLibrary();
        List<Song> allSongs = library.getSongs();
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
        model.addAttribute("title", "Add/Remove Songs from Setlist " + theSetlist.getVenue());
        return "setlist/edit";

    }

    @RequestMapping(value = "edit/{setlistId}", method = RequestMethod.POST)
    public String processEditSetlist(Model model,
                                     @PathVariable int setlistId,
                                     @RequestParam(value = "songs", required = false)int[] songs,
                                     @RequestParam(value = "deletedSongs", required = false)int[] deletedSongs){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Library library = user.getLibrary();
        Setlist theSetlist = setlistDao.findById(setlistId).get();
        List<Song> setlistSongs = theSetlist.getSongs();
        List<Song> theSongs = new ArrayList<Song>();
        if(songs != null) {
            for (int songId : songs) {
                theSetlist.addSong(songDao.findById(songId).get());
            }
        }
        if (deletedSongs != null){
        for (int songId : deletedSongs){
            Song theSong = songDao.findById(songId).get();
            setlistSongs.remove(theSong);
        }
        }
        theSetlist.setSongs(setlistSongs);
        setlistDao.save(theSetlist);
        setlistSongs = theSetlist.getSongs();
        List<Song> allSongs = library.getSongs();
        for (Song song : allSongs){
            if (setlistSongs.contains(song)){
                continue;
            }
            else{
                theSongs.add(song);
            }
        }
        String date = theSetlist.getMonth() + "/" + theSetlist.getDay() + "/" + theSetlist.getYear();
        String totalTime = theSetlist.getTotalTime();
        setlistSongs = theSetlist.getSongs();
        model.addAttribute("setlistSongs", setlistSongs);
        model.addAttribute("setlist", theSetlist);
        model.addAttribute("date", date);
        model.addAttribute("songs", theSongs);
        model.addAttribute("time", totalTime );
        model.addAttribute("title", "Add/Remove Songs from Setlist " + theSetlist.getVenue());

        return "setlist/edit";
    }
    @RequestMapping(value = "view/{setlistId}", method = RequestMethod.GET)
    public String viewSetlist(Model model, @PathVariable int setlistId){
        Setlist theSetlist = setlistDao.findById(setlistId).get();
        List<Song> theSongs = theSetlist.getSongs();
        if (theSongs.isEmpty()){
            model.addAttribute("setlist", theSetlist);
            return "setlist/empty";
        }
        String date = theSetlist.getMonth() + "/" + theSetlist.getDay() + "/" + theSetlist.getYear();
        String totalTime = theSetlist.getTotalTime();
        List<Song> allSongs = theSetlist.getSongs();
        model.addAttribute("setlist", theSetlist);
        model.addAttribute("date", date);
        model.addAttribute("songs", allSongs);
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

    @RequestMapping(value = "reorder/{setlistId}", method = RequestMethod.GET)
        public String displayReorderSetlist(Model model, @PathVariable int setlistId){
        Setlist theSetlist = setlistDao.findById(setlistId).get();
        List<Song> theSongs = theSetlist.getSongs();
        model.addAttribute("songs", theSetlist.getSongs());
        model.addAttribute("setlist", theSetlist);
        String date = theSetlist.getMonth() + "/" + theSetlist.getDay() + "/" + theSetlist.getYear();
        model.addAttribute("date", date);
        String totalTime = theSetlist.getTotalTime();
        model.addAttribute("time", totalTime);
        return "setlist/reorder";


    }
    @RequestMapping(value = "reorder/{setlistId}/{songId}/{direction}", method = RequestMethod.GET)
    public String reorderSetlist(Model model, @PathVariable int setlistId, @PathVariable int songId, @PathVariable String direction){
        Setlist theSetlist = setlistDao.findById(setlistId).get();
        List<Song> theSongs = theSetlist.getSongs();
        int songIndex = theSongs.indexOf(songDao.findById(songId).get());
        int otherIndex;
        if (direction.equals("down")){
            otherIndex = songIndex + 1;
        }
        else {
            otherIndex = songIndex - 1;
        }
        Collections.swap(theSongs,otherIndex, songIndex);
        Collections.reverse(theSongs);
        theSetlist.setSongs(theSongs);
        setlistDao.save(theSetlist);
        theSetlist = setlistDao.findById(setlistId).get();
        model.addAttribute("songs", theSetlist.getSongs());
        model.addAttribute("setlist", theSetlist);
        String date = theSetlist.getMonth() + "/" + theSetlist.getDay() + "/" + theSetlist.getYear();
        model.addAttribute("date", date);
        String totalTime = theSetlist.getTotalTime();
        model.addAttribute("time", totalTime);
        return "redirect:/setlist/reorder/" + setlistId;


    }
}


