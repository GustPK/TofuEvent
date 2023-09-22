package cs211.project.models;

public class Team {
    private String teamName;
    private String eventName;

    public Team(String teamName, String eventName) {
        this.teamName = teamName;
        this.eventName = eventName;
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
