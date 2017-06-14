package edu.mum.cs545.domain;

import javax.persistence.Entity;

@Entity
public class TournamentMatch extends Match {
    private int homePoints;
    private int visitorPoints;

    public int getHomePoints() {
        return homePoints;
    }

    public void setHomePoints(int homePoints) {
        this.homePoints = homePoints;
    }

    public int getVisitorPoints() {
        return visitorPoints;
    }

    public void setVisitorPoints(int visitorPoints) {
        this.visitorPoints = visitorPoints;
    }
}
