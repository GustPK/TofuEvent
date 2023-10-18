package cs211.project.models.collections;

import cs211.project.models.event.Schedule;

import java.util.ArrayList;

public class ScheduleList {
    private ArrayList<Schedule> scheduleList;
    public ArrayList<Schedule> getActivityList() {
        return scheduleList;
    }

    public ScheduleList() {
        scheduleList = new ArrayList<>();
    }
    public void addActivity(Schedule schedule) {
        scheduleList.add(schedule);
    }


}
