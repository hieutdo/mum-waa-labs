package edu.mum.cs545.service;

import edu.mum.cs545.domain.Match;
import edu.mum.cs545.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    private MatchRepository matchRepository;

    @Override
    public Iterable<Match> listAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public Match getMatchById(Integer id) {
        return matchRepository.findOne(id);
    }

    @Override
    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public void deleteMatch(Integer id) {
        matchRepository.delete(id);
    }
}
