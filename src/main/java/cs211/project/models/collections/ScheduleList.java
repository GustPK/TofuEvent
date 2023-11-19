package cs211.project.models.collections;

import cs211.project.models.event.Schedule;
import cs211.project.models.event.Team;

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


    public ScheduleList filterSchedulesByEventAndTeamName(String eventName,String key) {
        ScheduleList scheduleListTemp = new ScheduleList();
        for (Schedule schedule : scheduleList) {
            if (schedule.getEventName().equals(eventName) && schedule.getTeamName().equals(key)) {
                scheduleListTemp.addActivity(schedule);
            }
        }
        return scheduleListTemp;
    }
    public void setScheduleList(String oldName,String newName){
        for(Schedule schedule : scheduleList){
            if(schedule.getEventName().equals(oldName)){
                schedule.setEventName(newName);
            }
        }
    }

}
