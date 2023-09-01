package cs211.project.models.collections;

import cs211.project.models.event.Event;

import java.util.ArrayList;

public class EventList {
    private ArrayList<Event> eventList ;

    public EventList() {
         eventList = new ArrayList<>();
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void addEvent(String name, String imgSrc){
        String img = getClass().getResource("/images/"+imgSrc).toString();
        eventList.add(new Event(name ,img));
    }
}
