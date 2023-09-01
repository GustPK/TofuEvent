package cs211.project.model;

import java.util.ArrayList;

public class TeamList {

    private ArrayList<Team> teams;

    public TeamList() {
        teams = new ArrayList<>();
    }

    public void addNewTeam(String name) {
        if (!name.equals("")) {
            teams.add(new Team(name));
        }
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

}
