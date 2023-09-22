package cs211.project.models;

import cs211.project.models.Event;
import cs211.project.models.account.LoggedInAccount;

import java.io.File;
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
        File file = new File("data/images", imgSrc);
        String path = "file:///" + file.getAbsolutePath();
        eventList.add(new Event(name , path));
    }
}
