package edu.launchcode.setlist.controllers;

import edu.launchcode.setlist.models.Setlist;
import edu.launchcode.setlist.models.data.SetlistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("setlist")
public class SetlistController {

    @Autowired
    SetlistDao setlistDao;

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
}
