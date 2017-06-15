package edu.mum.cs545.bootstrap;

import edu.mum.cs545.domain.Match;
import edu.mum.cs545.domain.Stadium;
import edu.mum.cs545.domain.Team;
import edu.mum.cs545.repository.MatchRepository;
import edu.mum.cs545.repository.StadiumRepository;
import edu.mum.cs545.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Team team1 = new Team("Team 1", "City 1", "Mascot 1", "HomeUniform 1", "VisitorUniform 1");
            List<String> team1Players = new ArrayList<>();
            team1Players.add("player 1");
            team1Players.add("player 2");
            team1.setPlayers(team1Players);
            teamRepository.save(team1);

            Team team2 = new Team("Team 2", "City 2", "Mascot 2", "HomeUniform 2", "VisitorUniform 2");
            List<String> team2Players = new ArrayList<>();
            team2Players.add("player 3");
            team2Players.add("player 4");
            team2.setPlayers(team2Players);
            teamRepository.save(team2);

            Stadium stadium1 = new Stadium("Stadium 1", "City 1", "State 1");
            stadiumRepository.save(stadium1);

            Stadium stadium2 = new Stadium("Stadium 2", "City 2", "State 2");
            stadiumRepository.save(stadium2);

            Date date1 = sdf.parse("2017-01-01 00:00:00");
            Date starTime1 = sdf.parse("2017-01-01 01:00:00");
            Match match1 = new Match(date1, starTime1, 0, 0, stadium1, team1, team2);
            matchRepository.save(match1);

            Date date2 = sdf.parse("2017-01-02 00:00:00");
            Date starTime2 = sdf.parse("2017-01-02 01:00:00");
            Match match2 = new Match(date2, starTime2, 0, 0, stadium2, team2, team1);
            matchRepository.save(match2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
