package edu.mum.cs545.controller;

import edu.mum.cs545.domain.Match;
import edu.mum.cs545.service.MatchService;
import edu.mum.cs545.service.StadiumService;
import edu.mum.cs545.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/matches")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private StadiumService stadiumService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String newMatch(Model model) {
        model.addAttribute("match", new Match());
        model.addAttribute("teams", teamService.listAllTeams());
        model.addAttribute("stadiums", stadiumService.listAllStadiums());
        return "match/detail";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String editMatch(@PathVariable Integer id, Model model) {
        model.addAttribute("match", matchService.getMatchById(id));
        model.addAttribute("teams", teamService.listAllTeams());
        model.addAttribute("stadiums", stadiumService.listAllStadiums());
        return "match/detail";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listMatches(Model model) {
        model.addAttribute("matches", matchService.listAllMatches());
        return "match/list";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveMatch(Match match) {
        matchService.saveMatch(match);
        return "redirect:/matches/detail/" + match.getId();
    }

    @RequestMapping("/delete/{id}")
    public String deleteMatch(@PathVariable Integer id) {
        matchService.deleteMatch(id);
        return "redirect:/matches";
    }
}
