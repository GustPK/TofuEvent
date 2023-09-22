package cs211.project.models;

import cs211.project.models.Activity;

import java.util.ArrayList;

public class ActivityList {
    private ArrayList<Activity> activityList;

    public ActivityList() {
        activityList = new ArrayList<>();
    }

    public void addActivity(String teamName, String time, String activity, String eventName) {
        activityList.add(new Activity(teamName, time, activity, eventName));
    }

    public Activity findActivityByTeamName(String teamName) {
        for (Activity activity : activityList) {
            if (activity.getTeamName().equals(teamName)) {
                return activity;
            }
        }
        return null;
    }

    public ArrayList<Activity> getActivityList() {
        return activityList;
    }
}
