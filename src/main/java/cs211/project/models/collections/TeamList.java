package cs211.project.models.collections;

import cs211.project.models.Team;

import java.util.ArrayList;

public class TeamList {

    private ArrayList<Team> teams;

    public TeamList() {
        teams = new ArrayList<>();
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

}
