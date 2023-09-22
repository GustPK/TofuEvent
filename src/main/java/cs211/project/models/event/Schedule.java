package cs211.project.models.event;

public class Schedule {
    private String nameEvent;
    private String team;
    private String schedule;

    public Schedule(String nameEvent, String team, String schedule) {
        this.nameEvent = nameEvent;
        this.team = team;
        this.schedule = schedule;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public String getTeam() {
        return team;
    }

    public String getSchedule() {
        return schedule;
    }
}
