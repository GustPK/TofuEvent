package cs211.project.models.event;

public class Participant {
    private String userName;
    private String event;
    private String team;
    private String ban;

    public Participant(String userName, String event, String team, String ban) {
        this.userName = userName;
        this.event = event;
        this.team = team;
        this.ban = ban;
    }

    public String getUsername() {
        return userName;
    }

    public String getEvent() {
        return event;
    }

    public String getTeamName() {
        return team;
    }

    public String getBan() {
        return ban;
    }

    public void setEvent(String eventName){this.event = eventName;}
    public void setBan(String banStatus) {
        this.ban = banStatus;
    }
    public boolean checkEventName(String event){
        return this.event.equals(event);
    }
    public boolean checkTeamName(String team){
        return this.team.equals(team);
    }
    public boolean checkName(String name){
        return this.userName.equals(name);
    }
    public boolean isBanned(){
        return this.ban.equals("banned");
    }
}
