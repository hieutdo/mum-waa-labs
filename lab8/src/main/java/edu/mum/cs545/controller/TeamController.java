package edu.mum.cs545.controller;

import edu.mum.cs545.domain.Team;
import edu.mum.cs545.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String newTeam(Model model) {
        model.addAttribute("team", new Team());
        return "team/detail";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String editTeam(@PathVariable Integer id, Model model) {
        model.addAttribute("team", teamService.getTeamById(id));
        return "team/detail";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listTeams(Model model) {
        model.addAttribute("teams", teamService.listAllTeams());
        return "team/list";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveTeam(Team team) {
        teamService.saveTeam(team);
        return "redirect:/teams/detail/" + team.getId();
    }

    @RequestMapping("/delete/{id}")
    public String deleteTeam(@PathVariable Integer id) {
        teamService.deleteTeam(id);
        return "redirect:/teams";
    }
}
