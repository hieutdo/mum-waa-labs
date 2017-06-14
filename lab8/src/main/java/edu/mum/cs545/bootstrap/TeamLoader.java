package edu.mum.cs545.bootstrap;

import edu.mum.cs545.domain.Team;
import edu.mum.cs545.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeamLoader implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(TeamLoader.class);

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
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

    }
}
