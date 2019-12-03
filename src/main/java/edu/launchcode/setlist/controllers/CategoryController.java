package edu.launchcode.setlist.controllers;


import edu.launchcode.setlist.models.Song;
import edu.launchcode.setlist.models.Category;
import edu.launchcode.setlist.models.data.SongDao;
import edu.launchcode.setlist.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SongDao songDao;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add Category");
        model.addAttribute(new Category());
        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddSonglist(@ModelAttribute @Valid Category newCategory, Errors errors, Model model) {
        categoryDao.save(newCategory);
        model.addAttribute("songlist", newCategory);
        model.addAttribute("title", newCategory.getName());
        return "category/index";
    }

    @RequestMapping(value = "edit/{categoryId}", method = RequestMethod.GET)
    public String editSonglist(Model model, @PathVariable int categoryId) {

        Iterable<Song> allSongs = songDao.findAll();
        List<Song> availableSongs = new ArrayList<Song>();
        Category theCategory = categoryDao.findById(categoryId).get();
        List<Song> categorySongs = theCategory.getSongs();
        for (Song song : allSongs) {
            if (categorySongs.contains(song)) {
                continue;
            } else {
                availableSongs.add(song);
            }
        }
        model.addAttribute("categorySongs", categorySongs);
        model.addAttribute("availableSongs", availableSongs);
        model.addAttribute("title", "Edit category: " + theCategory.getName());
        model.addAttribute("category", theCategory);
        return "category/edit";
    }

    @RequestMapping(value = "edit/{categoryId}", method = RequestMethod.POST)
    public String editSonglist(Model model, @PathVariable int categoryId, @RequestParam(value = "songs", required = false) int[] songs,
                               @RequestParam(value = "deletedSongs", required = false) int[] deletedSongs) {
        Category theCategory = categoryDao.findById(categoryId).get();
        List<Song> categorySongs = theCategory.getSongs();
        if(songs!=null) {
            for (int songID : songs) {
                Song theSong = songDao.findById(songID).get();
                categorySongs.add(theSong);
            }
        }
        if (deletedSongs != null){
            for (int deletedSongId : deletedSongs){
                Song theSong = songDao.findById(deletedSongId).get();
                categorySongs.remove(theSong);
            }
        }
        theCategory.setSongs(categorySongs);
        categoryDao.save(theCategory);
        Iterable<Song> allSongs = songDao.findAll();
        List<Song> availableSongs = new ArrayList<Song>();

        categorySongs = theCategory.getSongs();
        for (Song song : allSongs) {
            if (categorySongs.contains(song)) {
                continue;
            } else {
                availableSongs.add(song);
            }
        }
        model.addAttribute("categorySongs", categorySongs);
        model.addAttribute("availableSongs", availableSongs);
        model.addAttribute("title", "Edit category: " + theCategory.getName());
        model.addAttribute("category", theCategory);
        return "category/edit";
    }

    @RequestMapping(value = "delete/{categoryId}", method = RequestMethod.GET)
    public String deleteCategory(Model model, @PathVariable int categoryId){
        Category theCategory = categoryDao.findById(categoryId).get();
        categoryDao.delete(theCategory);
        model.addAttribute("message", "Category " + theCategory.getName() + " has been successfully deleted!");
        return "category/delete";
    }
    @RequestMapping(value = "view/{categoryId}", method = RequestMethod.GET)
    public String viewCategory(Model model, @PathVariable int categoryId){
        Category theCategory = categoryDao.findById(categoryId).get();
        List<Song> songs = theCategory.getSongs();
        model.addAttribute("songs", songs);
        model.addAttribute("category", theCategory);
        return "category/view";

    }
}





