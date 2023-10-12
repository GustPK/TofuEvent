package cs211.project.models.collections;

import cs211.project.models.event.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public void sort(Comparator<Event> cmp) {
        Collections.sort(events, cmp);
    }
    public Event findByEventName(String eventName){
        for(Event event:events){
            if (event.getName().equals(eventName))
                return event;
        }
        return null;
    }

}
