package cs211.project.models.event;

public class Eva {
    private String date;
    private String name ;


    public Eva(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() { return name; }

    public String getDate() { return date; }
}
