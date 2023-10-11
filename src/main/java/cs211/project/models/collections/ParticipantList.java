package cs211.project.models.collections;

import cs211.project.models.event.Participant;

import java.util.ArrayList;

public class ParticipantList {
    private ArrayList<Participant> participants;

    public ParticipantList(){
        participants = new ArrayList<>();
    }

    public ArrayList<Participant> getParticipants(){
        return participants;
    }

    public void addParticipant(Participant participant){
        participants.add(participant);
    }
}
