package edu.mum.cs545.service;

import edu.mum.cs545.domain.Team;

public interface TeamService {
    Iterable<Team> listAllTeams();

    Team getTeamById(Integer id);

    Team saveTeam(Team team);

    void deleteTeam(Integer id);
}
