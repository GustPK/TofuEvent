package cs211.project.models;

public class Team {
    private String teamName;
    private String eventName;
    private String joinFieldText;

    public Team(String eventName, String teamName, String joinFieldText) {
        this.eventName = eventName;
        this.teamName = teamName;
        this.joinFieldText = joinFieldText;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getEventName() {
        return eventName;
    }
    public String getJoinFieldText() {return joinFieldText; }


    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", eventName='" + eventName + '\'' +
                '}';
    }
}
