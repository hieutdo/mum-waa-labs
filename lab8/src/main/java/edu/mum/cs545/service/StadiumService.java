package edu.mum.cs545.service;

import edu.mum.cs545.domain.Stadium;

public interface StadiumService {
    Iterable<Stadium> listAllStadiums();

    Stadium getStadiumById(Integer id);

    Stadium saveStadium(Stadium Stadium);

    void deleteStadium(Integer id);
}
