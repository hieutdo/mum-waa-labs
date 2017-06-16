package edu.mum.cs545.rest;

import edu.mum.cs545.domain.Stadium;
import edu.mum.cs545.service.StadiumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class StadiumResource {
    private StadiumService stadiumService;

    public StadiumResource(StadiumService stadiumService) {
        this.stadiumService = stadiumService;
    }

    @GetMapping("/stadiums")
    public ResponseEntity<Iterable<Stadium>> getAllStadiums() {
        return new ResponseEntity<>(stadiumService.listAllStadiums(), HttpStatus.OK);
    }

    @GetMapping("/stadiums/{id}")
    public ResponseEntity<Stadium> getStadium(@PathVariable Integer id) {
        Stadium stadium = stadiumService.getStadiumById(id);
        if (stadium == null) {
            return new ResponseEntity<Stadium>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Stadium>(stadium, HttpStatus.OK);
    }

    @PostMapping("/stadiums")
    public ResponseEntity<Stadium> createStadium(@Valid @RequestBody Stadium stadium) throws URISyntaxException {
        if (stadium.getId() != null) {
            return ResponseEntity.badRequest().body(null);
        }
        Stadium result = stadiumService.saveStadium(stadium);
        return ResponseEntity.created(new URI("/api/stadiums/" + result.getId()))
                .body(result);
    }

    @PutMapping("/stadiums")
    public ResponseEntity<Stadium> updateStadium(@Valid @RequestBody Stadium stadium) throws URISyntaxException {
        if (stadium.getId() == null) {
            return createStadium(stadium);
        }
        Stadium result = stadiumService.saveStadium(stadium);
        return ResponseEntity.ok(stadium);
    }

    @DeleteMapping("/stadiums/{id}")
    public ResponseEntity<Void> deleteStadium(@PathVariable Integer id) {
        stadiumService.deleteStadium(id);
        return ResponseEntity.ok().build();
    }
}
