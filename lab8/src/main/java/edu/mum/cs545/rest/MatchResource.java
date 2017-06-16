package edu.mum.cs545.rest;

import edu.mum.cs545.domain.Match;
import edu.mum.cs545.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class MatchResource {
    private MatchService matchService;

    public MatchResource(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/matches")
    public ResponseEntity<Iterable<Match>> getAllMatchs() {
        return new ResponseEntity<>(matchService.listAllMatches(), HttpStatus.OK);
    }

    @GetMapping("/matches/{id}")
    public ResponseEntity<Match> getMatch(@PathVariable Integer id) {
        Match match = matchService.getMatchById(id);
        if (match == null) {
            return new ResponseEntity<Match>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Match>(match, HttpStatus.OK);
    }

    @PostMapping("/matches")
    public ResponseEntity<Match> createMatch(@Valid @RequestBody Match match) throws URISyntaxException {
        if (match.getId() != null) {
            return ResponseEntity.badRequest().body(null);
        }
        Match result = matchService.saveMatch(match);
        return ResponseEntity.created(new URI("/api/matches/" + result.getId()))
                .body(result);
    }

    @PutMapping("/matches")
    public ResponseEntity<Match> updateMatch(@Valid @RequestBody Match match) throws URISyntaxException {
        if (match.getId() == null) {
            return createMatch(match);
        }
        Match result = matchService.saveMatch(match);
        return ResponseEntity.ok(match);
    }

    @DeleteMapping("/matches/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Integer id) {
        matchService.deleteMatch(id);
        return ResponseEntity.ok().build();
    }
}
