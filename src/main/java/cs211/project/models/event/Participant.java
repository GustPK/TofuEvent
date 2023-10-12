package cs211.project.models.event;

public class Participant {
    private String userName;
    private String event;
    private String team;
    private String ban;

    public Participant(String userName, String event, String team) {
        this.userName = userName;
        this.event = event;
        this.team = team;
        this.ban = "unbanned";
    }

    public String getUsername() {
        return userName;
    }

    public String getEvent() {
        return event;
    }

    public String getTeamName() {
        return team;
    }

    public String getBan() {
        return ban;
    }

    public void setBanned() {
        this.ban = "banned";
    }
}
