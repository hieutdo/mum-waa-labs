package edu.mum.cs545.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Match {
    @Id
    @GeneratedValue
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @DateTimeFormat(pattern = "hh:mm:ss")
    private Date startTime;

    private int homeScore;

    private int visitorScore;

    @ManyToOne
    private Stadium stadium;

    @ManyToOne
    private Team visitorTeam;

    @ManyToOne
    private Team homeTeam;

    public Match() {
    }

    public Match(Date date, Date startTime, int homeScore, int visitorScore,
                 Stadium stadium, Team visitorTeam, Team homeTeam) {
        this.date = date;
        this.startTime = startTime;
        this.homeScore = homeScore;
        this.visitorScore = visitorScore;
        this.stadium = stadium;
        this.visitorTeam = visitorTeam;
        this.homeTeam = homeTeam;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public void setStadium(Stadium stadium) {
        this.stadium = stadium;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getVisitorScore() {
        return visitorScore;
    }

    public void setVisitorScore(int visitorScore) {
        this.visitorScore = visitorScore;
    }

    public Team getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(Team visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }
}
