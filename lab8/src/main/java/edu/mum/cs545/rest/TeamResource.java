package edu.mum.cs545.rest;

import edu.mum.cs545.domain.Team;
import edu.mum.cs545.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class TeamResource {
    private TeamService teamService;

    public TeamResource(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/teams")
    public ResponseEntity<Iterable<Team>> getAllTeams() {
        return new ResponseEntity<>(teamService.listAllTeams(), HttpStatus.OK);
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<Team> getTeam(@PathVariable Integer id) {
        Team team = teamService.getTeamById(id);
        if (team == null) {
            return new ResponseEntity<Team>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Team>(team, HttpStatus.OK);
    }

    @PostMapping("/teams")
    public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team) throws URISyntaxException {
        if (team.getId() != null) {
            return ResponseEntity.badRequest().body(null);
        }
        Team result = teamService.saveTeam(team);
        return ResponseEntity.created(new URI("/api/teams/" + result.getId()))
                .body(result);
    }

    @PutMapping("/teams")
    public ResponseEntity<Team> updateTeam(@Valid @RequestBody Team team) throws URISyntaxException {
        if (team.getId() == null) {
            return createTeam(team);
        }
        Team result = teamService.saveTeam(team);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok().build();
    }
}
