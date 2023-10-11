package cs211.project.models;

public class Schedule {
    private String eventName;
    private String time;
    private String activity;
    private String teamName;
    private String date;

    public Schedule(String eventName, String teamName, String activity, String time, String date) {
        this.eventName = eventName;
        this.teamName = teamName;
        this.activity = activity;
        this.time = time;
        this.date = date;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTime() {
        return time;
    }
    public String getDate() {
        return date;
    }

    public String getActivity() {
        return activity;
    }

    public String getEventName() {
        return eventName;
    }
}
