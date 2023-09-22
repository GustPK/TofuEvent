package cs211.project.models.event;

import cs211.project.services.Datasource;

import java.util.ArrayList;

public class Event {
    private String date;
    private String name ;
    private String imgEvent;


    public Event(String name, String date, String imgEvent) {
        this.name = name;
        this.date = date;
        this.imgEvent = imgEvent;
    }

    public String getName() { return name; }
    public String getDate() { return date; }
    public String getImgEvent() {return imgEvent; }

}
