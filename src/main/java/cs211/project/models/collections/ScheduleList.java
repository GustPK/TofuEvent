package cs211.project.models.collections;

import cs211.project.models.account.Account;
import cs211.project.models.event.Event;
import cs211.project.models.event.Schedule;

import java.util.ArrayList;

public class ScheduleList {
    private ArrayList<Schedule> schedules;
    public ScheduleList() { schedules = new ArrayList<>(); }
    public ArrayList<Schedule> getSchedules(){return schedules;}
    public void addSchedule(Schedule schedule){
        schedules.add(schedule);
    }
}
