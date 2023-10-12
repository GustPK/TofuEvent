package cs211.project.services;

import cs211.project.models.collections.ParticipantList;
import cs211.project.models.event.Participant;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ParticipantListDatasource implements Datasource<ParticipantList> {
    private String fileName = "data" + File.separator + "Participant.csv";

    public ParticipantListDatasource() {
        checkFileIsExisted();
    }
    private void checkFileIsExisted() {
        File file = new File("data");
        if (!file.exists()) {
            file.mkdir();
        }
        file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public ParticipantList readData() {
        BufferedReader buffer = null;
        FileInputStream fileInputStream = null;

        ParticipantList participantLists = new ParticipantList();
        File file = new File(fileName);

        try {
            fileInputStream = new FileInputStream(file);

            InputStreamReader inputStreamReader = new InputStreamReader(
                    fileInputStream, StandardCharsets.UTF_8);

            buffer = new BufferedReader(inputStreamReader);
            String line = "";

            while ((line = buffer.readLine()) != null){
                if (line.equals("")) continue;
                String[] data = line.split(",");

                String userName = data[0].trim();
                String event = data[1].trim();
                String team = data[2].trim();

                participantLists.addParticipant(new Participant(userName,event,team));
            }
            buffer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return participantLists;
    }

    @Override
    public void writeData(ParticipantList data) {
        BufferedWriter buffer = null;
        FileOutputStream fileOutputStream;
        File file = new File(fileName);
        try {
            fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    fileOutputStream,StandardCharsets.UTF_8);
            buffer = new BufferedWriter(outputStreamWriter);

            for (Participant participant : data.getParticipants()){
                String line = participant.getUsername()+","
                        + participant.getEvent()+","
                        + participant.getTeamName()+","
                        + participant.getBan();

                buffer.append(line);
                buffer.append("\n");
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        } finally {
            try{
                buffer.flush();
                buffer.close();
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }

    }

}
