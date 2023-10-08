package cs211.project.models.event;

import cs211.project.services.Datasource;

import java.util.ArrayList;

public class Event {
    private String organizer;
    private String date;
    private String name ;
    private String imgEvent;



    public Event(String organizer, String name, String date, String imgEvent) {
        this.organizer = organizer;
        this.name = name;
        this.date = date;
        this.imgEvent = imgEvent;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getImgEvent() {return imgEvent; }
    public String getOrganizer() {return organizer; }

}
