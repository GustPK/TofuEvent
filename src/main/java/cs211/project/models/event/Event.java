package cs211.project.models.event;

public class Event {
    private String organizer;
    private String dateStart;
    private String dateEnd;
    private String name;
    private String imgEvent;
    private String startTime;
    private String endTime;
    private String desc;
    private String maximum;
    private String joinedText;
    private String status;
    public String tamp;
    public Event(String organizer, String name, String dateStart, String dateEnd, String startTime, String endTime, String desc, String maximum, String joinedText, String status, String imgEvent) {
        this.organizer = organizer;
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.startTime = startTime;
        this.endTime = endTime;
        this.desc = desc;
        this.maximum = maximum;
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
    public String getMaximum() {return maximum; }
    public String getStatus() {return status; }
    public void addJoin() {
        int n = Integer.parseInt(joinedText);
        n++;
        joinedText = Integer.toString(n);
    }
    public void setEditEvent(String name, String des, String maximum, String picture, String startTime, String endTime, String dateStart, String dateEnd) {
        this.desc = des;
        this.maximum = maximum;
        this.imgEvent = picture;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.dateEnd = dateEnd;
        this.dateStart = dateStart;
    }

    public int[] splitDate(String date) {
        String[] data = date.split("-");
        int[] result = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            result[i] = Integer.parseInt(data[i]);
        }

        return result;
    }
    public int[] splitTime(String date) {
        String[] data = date.split(":");
        int[] result = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            result[i] = Integer.parseInt(data[i]);
        }

        return result;
    }

}
