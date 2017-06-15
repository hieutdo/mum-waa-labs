package edu.mum.cs545.service;

import edu.mum.cs545.domain.Match;

public interface MatchService {
    Iterable<Match> listAllMatches();

    Match getMatchById(Integer id);

    Match saveMatch(Match Match);

    void deleteMatch(Integer id);
}
