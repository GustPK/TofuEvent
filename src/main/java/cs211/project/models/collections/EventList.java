package cs211.project.models.collections;

import cs211.project.models.event.Event;
import cs211.project.models.event.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EventList {
    private ArrayList<Event> events;
    public ArrayList<Event> getEvents(){
        return events;
    }
    public EventList(){
        events = new ArrayList<>();
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
    public boolean isEventNameDuplicate(String name) {
        for (Event event : events) {
            if (event.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public EventList findEventByUserName(String name){
        EventList eventsTemp = new EventList();
        for (Event event : events) {
            if(event.getOrganizer().equals(name)){
                eventsTemp.addEvent(event);
            }
        }
        return eventsTemp;
    }


}
