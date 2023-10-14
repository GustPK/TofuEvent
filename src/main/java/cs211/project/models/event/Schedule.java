package cs211.project.models.event;

public class Schedule {
    private String eventName;
    private String time;
    private String activity;
    private String teamName;
    private String date;
    private String status;

    public Schedule(String eventName, String teamName, String activity, String time, String date,String status) {
        this.eventName = eventName;
        this.teamName = teamName;
        this.activity = activity;
        this.time = time;
        this.date = date;
        this.status = status;

    }
    public Schedule(String eventName, String teamName, String activity, String time, String date) {
        this.eventName = eventName;
        this.teamName = teamName;
        this.activity = activity;
        this.time = time;
        this.date = date;
        this.status = "undone";

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

    public String getStatus() { return status; }

    public void setStatusDone() {
        this.status = "done";
    }
    public void setStatusUndone() {
        this.status = "undone";
    }
    public void setEventName(String eventName){this.eventName = eventName;}
}
