package cs211.project.models.collections;

import cs211.project.models.event.Team;

import java.util.ArrayList;

public class TeamList {
    private ArrayList<Team> teams;
    public ArrayList<Team> getTeams() { return teams; }
    public TeamList() {
        teams = new ArrayList<>();
    }
    public void addTeam(Team team) {
        teams.add(team);
    }

    public Team findByTeamName(String teamName){
        for(Team team:teams){
            if (team.getTeamName().equals(teamName))
                return team;
        }
        return null;
    }
    public TeamList findByEventNameList(String eventName){
        TeamList teamList = new TeamList();
        for(Team team : teams){
            if (team.getEventName().equals(eventName))
                teamList.addTeam(team);
        }
        return teamList;
    }
    public boolean isTeamNameDuplicate(String eventName, String teamName) {
        for (Team team : teams) {
            if (team.getEventName().equals(eventName) && team.getTeamName().equals(teamName)) {
                return true;
            }
        }
        return false;
    }
    public void setTeams(String oldName,String newName){
        for(Team team : teams){
            if(team.getEventName().equals(oldName)){
                team.setEventName(newName);
            }
        }
    }

}
