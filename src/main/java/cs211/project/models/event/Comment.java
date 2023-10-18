package cs211.project.models.event;

public class Comment {
    private String teamName;
    private String comment;
    private String eventName;
    private String username;

    public Comment(String teamName, String comment, String eventName, String username) {
        this.teamName = teamName;
        this.comment = comment;
        this.eventName = eventName;
        this.username = username;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getUsername() { return username;}

    public String getComment() {
        return comment;
    }

    public String getEventName() {
        return eventName;
    }
    public void setUsername(String name) {this.username = name; }

    @Override
    public String toString() {
        return username+": "+comment;
    }
}
