package cs211.project.services;

import cs211.project.model.TeamList;

public class TeamHardCodeDatasource {
    public TeamList readData() {
        TeamList list = new TeamList();
        list.addNewTeam("First");
        list.addNewTeam("Second");
        list.addNewTeam("Third");
        list.addNewTeam("Fourth");
        return list;
    }
}
