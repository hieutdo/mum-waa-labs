package edu.mum.cs545.service;

import edu.mum.cs545.domain.Stadium;
import edu.mum.cs545.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StadiumServiceImpl implements StadiumService {
    @Autowired
    private StadiumRepository stadiumRepository;

    @Override
    public Iterable<Stadium> listAllStadiums() {
        return stadiumRepository.findAll();
    }

    @Override
    public Stadium getStadiumById(Integer id) {
        return stadiumRepository.findOne(id);
    }

    @Override
    public Stadium saveStadium(Stadium stadium) {
        return stadiumRepository.save(stadium);
    }

    @Override
    public void deleteStadium(Integer id) {
        stadiumRepository.delete(id);
    }
}
