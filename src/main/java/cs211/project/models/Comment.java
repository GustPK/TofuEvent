package cs211.project.models;

public class Comment {
    private String teamName;
    private String comment;
    private String eventName;

    public Comment(String teamName, String comment, String eventName) {
        this.teamName = teamName;
        this.comment = comment;
        this.eventName = eventName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getComment() {
        return comment;
    }

    public String getEventName() {
        return eventName;
    }

    @Override
    public String toString() {
        return comment;
    }
}
