package cs211.project.models;

public class Team {
    private String teamName;
    private String eventName;

    public Team(String eventName, String teamName) {
        this.eventName = eventName;
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getEventName() {
        return eventName;
    }


    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}
