package cs211.project.models.event;

import cs211.project.models.account.Account;

import java.util.ArrayList;

public class EvaList {
    private ArrayList<Eva> events;

    public EvaList(){
        events = new ArrayList<>();
    }

    public ArrayList<Eva> getEvents(){
        return events;
    }

    public void addEvent(Eva event){
        events.add(event);
    }

}
