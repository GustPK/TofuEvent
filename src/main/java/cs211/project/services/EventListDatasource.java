package cs211.project.services;

import cs211.project.models.account.Account;
import cs211.project.models.event.Event;
import cs211.project.models.collections.EventList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class EventListDatasource implements Datasource<EventList> {
    private String fileName = "data" + File.separator + "EventList.csv";

    public EventListDatasource() {
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
    public EventList readData() {
        BufferedReader buffer = null;
        FileInputStream fileInputStream = null;

        EventList events = new EventList() ;
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

                String organizer = data[0].trim();
                String name = data[1].trim();
                String dateStart = data[2].trim();
                String dateEnd = data[3].trim();
                String startTime = data[4].trim();
                String endTime = data[5].trim();
                String desc = data[6].trim();
                String joinFieldText = data[7].trim();
                String status = data[8].trim();
                String image = data[9].trim();

                events.addEvent(new Event(organizer, name, dateStart, dateEnd, startTime, endTime, desc, joinFieldText, status, image));
            }
            buffer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public void writeData(EventList data) {
        BufferedWriter buffer = null;
        FileOutputStream fileOutputStream;
        File file = new File(fileName);
        try {
            fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    fileOutputStream,StandardCharsets.UTF_8);
            buffer = new BufferedWriter(outputStreamWriter);

            for (Event event : data.getEvents()){
                String line = event.getOrganizer()+","
                        + event.getName()+","
                        + event.getDateStart()+","
                        + event.getDateEnd()+","
                        + event.getStartTime()+","
                        + event.getEndTime()+","
                        + event.getDesc()+","
                        + event.getJoinFieldText()+","
                        + event.getStatus()+","
                        + event.getImgEvent();

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
