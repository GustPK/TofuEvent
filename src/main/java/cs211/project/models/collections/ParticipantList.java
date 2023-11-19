package cs211.project.models.collections;

import cs211.project.models.event.Participant;
import cs211.project.models.event.Team;

import java.util.ArrayList;

public class ParticipantList {
    private ArrayList<Participant> participants;
    public ArrayList<Participant> getParticipants(){
        return participants;
    }

    public ParticipantList(){
        participants = new ArrayList<>();
    }
    public void addParticipant(Participant participant){
        participants.add(participant);
    }
    public boolean isAlreadyJoined(String username, String eventName, String teamName) {
        return participants.stream()
                .anyMatch(p -> p.getUsername().equals(username) && p.getEvent().equals(eventName) && p.getTeamName().equals(teamName));
    }
    public void setParticipants(String oldName,String newName){
        for(Participant participant : participants){
            if(participant.getEvent().equals(oldName)){
                participant.setEvent(newName);
            }
        }
    }
}
