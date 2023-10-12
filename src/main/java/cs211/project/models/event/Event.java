package cs211.project.models.event;

import cs211.project.services.Datasource;

import java.util.ArrayList;

public class Event {
    private String organizer;
    private String dateStart;
    private String dateEnd;
    private String name;
    private String imgEvent;
    private String startTime;
    private String endTime;
    private String desc;
    private String joinFieldText;
    private String joinedText;
    private String status;
    public Event(String organizer, String name, String dateStart, String dateEnd, String startTime, String endTime, String desc, String joinFieldText, String joinedText, String status, String imgEvent) {
        this.organizer = organizer;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.startTime = startTime;
        this.endTime = endTime;
        this.desc = desc;
        this.joinFieldText = joinFieldText;
        this.joinedText = joinedText;
        this.status = status;
        this.imgEvent = imgEvent;
    }

    public String getJoinedText() {return joinedText;}
    public String getName() { return name; }
    public String getImgEvent() {return imgEvent; }
    public String getOrganizer() {return organizer; }
    public String getDateStart() { return dateStart; }
    public String getDateEnd() { return dateEnd; }
    public String getStartTime() {return startTime; }
    public String getEndTime() {return endTime; }
    public String getDesc() { return desc; }
    public String getJoinFieldText() {return joinFieldText; }
    public String getStatus() {return status; }
}
