package cs211.project.models.collections;

import cs211.project.models.event.Event;

import java.util.ArrayList;

public class EventList {
    private ArrayList<Event> events;

    public EventList(){
        events = new ArrayList<>();
    }

    public ArrayList<Event> getEvents(){
        return events;
    }

    public void addEvent(Event event){
        events.add(event);
    }


}
