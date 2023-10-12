package cs211.project.models;

import java.util.ArrayList;

public class ScheduleList {
    private ArrayList<Schedule> scheduleList;

    public ScheduleList() {
        scheduleList = new ArrayList<>();
    }

    public void addActivity(Schedule schedule) {
        scheduleList.add(schedule);
    }

    public Schedule findActivityByTeamName(String teamName) {
        for (Schedule schedule : scheduleList) {
            if (schedule.getTeamName().equals(teamName)) {
                return schedule;
            }
        }
        return null;
    }

    public ArrayList<Schedule> getActivityList() {
        return scheduleList;
    }

}
