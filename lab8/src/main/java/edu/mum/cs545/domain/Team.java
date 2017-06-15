package edu.mum.cs545.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String city;

    private String mascot;

    private String homeUniform;

    private String visitorUniform;

    @ElementCollection
    private List<String> players;

    @OneToMany(mappedBy = "homeTeam")
    private List<Match> matchesAsHome;

    @OneToMany(mappedBy = "visitorTeam")
    private List<Match> matchesAsVisitor;

    public Team() {
    }

    public Team(String name, String city, String mascot, String homeUniform, String visitorUniform) {
        this.name = name;
        this.city = city;
        this.mascot = mascot;
        this.homeUniform = homeUniform;
        this.visitorUniform = visitorUniform;
        this.players = new ArrayList<>();
        this.matchesAsHome = new ArrayList<>();
        this.matchesAsVisitor = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMascot() {
        return mascot;
    }

    public void setMascot(String mascot) {
        this.mascot = mascot;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public String getHomeUniform() {
        return homeUniform;
    }

    public void setHomeUniform(String homeUniform) {
        this.homeUniform = homeUniform;
    }

    public String getVisitorUniform() {
        return visitorUniform;
    }

    public void setVisitorUniform(String visitorUniform) {
        this.visitorUniform = visitorUniform;
    }

    public List<Match> getMatchesAsHome() {
        return matchesAsHome;
    }

    public void setMatchesAsHome(List<Match> matchesAsHome) {
        this.matchesAsHome = matchesAsHome;
    }

    public List<Match> getMatchesAsVisitor() {
        return matchesAsVisitor;
    }

    public void setMatchesAsVisitor(List<Match> matchesAsVisitor) {
        this.matchesAsVisitor = matchesAsVisitor;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
