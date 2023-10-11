package cs211.project.models.event;

public class Participant {
    private String userName;
    private String event;
    private String team;

    public Participant(String userName, String event, String team) {
        this.userName = userName;
        this.event = event;
        this.team = team;
    }

    public String getUserName() {
        return userName;
    }

    public String getEvent() {
        return event;
    }

    public String getTeam() {
        return team;
    }
}
