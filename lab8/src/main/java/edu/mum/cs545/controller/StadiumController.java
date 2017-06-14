package edu.mum.cs545.controller;

import edu.mum.cs545.domain.Stadium;
import edu.mum.cs545.service.StadiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/stadiums")
public class StadiumController {
    @Autowired
    private StadiumService stadiumService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String newStadium(Model model) {
        model.addAttribute("stadium", new Stadium());
        return "stadium/detail";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String editStadium(@PathVariable Integer id, Model model) {
        model.addAttribute("stadium", stadiumService.getStadiumById(id));
        return "stadium/detail";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listStadiums(Model model) {
        model.addAttribute("stadiums", stadiumService.listAllStadiums());
        return "stadium/list";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveStadium(Stadium stadium) {
        stadiumService.saveStadium(stadium);
        return "redirect:/stadiums/detail/" + stadium.getId();
    }

    @RequestMapping("/delete/{id}")
    public String deleteStadium(@PathVariable Integer id) {
        stadiumService.deleteStadium(id);
        return "redirect:/stadiums";
    }
}
