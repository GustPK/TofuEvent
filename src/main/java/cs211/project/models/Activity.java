package cs211.project.models;

public class Activity {
    private String teamName;
    private String time;
    private String activity;
    private String eventName;

    public Activity(String teamName, String time, String activity, String eventName) {
        this.teamName = teamName;
        this.time = time;
        this.activity = activity;
        this.eventName = eventName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTime() {
        return time;
    }

    public String getActivity() {
        return activity;
    }

    public String getEventName() {
        return eventName;
    }
}
