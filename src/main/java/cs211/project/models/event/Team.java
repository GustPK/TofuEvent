package cs211.project.models.event;

public class Team {
    private String teamName;
    private String eventName;
    private String joinFieldText;
    private String joinedText;

    public Team(String eventName, String teamName, String joinFieldText, String joinedText) {
        this.eventName = eventName;
        this.teamName = teamName;
        this.joinFieldText = joinFieldText;
        this.joinedText = joinedText;
    }

    public String getEventName() {
        return eventName;
    }
    public String getJoinFieldText() {return joinFieldText; }
    public String getJoinedText() {return joinedText; }
    public String getTeamName() {return teamName; }
    public void setEventName(String name){this.eventName = name;}
    public void addJoin() {
        int n = Integer.parseInt(joinedText);
        n++;
        joinedText = Integer.toString(n);
    }
    @Override
    public String toString() {
        return teamName;
    }
}
