package cs211.project.services;

import cs211.project.models.event.Schedule;
import cs211.project.models.collections.ScheduleList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ScheduleFileDatasource implements Datasource<ScheduleList> {
    private String fileName = "data" + File.separator + "schedule.csv";

    public ScheduleFileDatasource() { checkFileIsExisted(); }

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
    public ScheduleList readData() {
        ScheduleList scheduleList = new ScheduleList();
        File file = new File(fileName);

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(
                fileInputStream,
                StandardCharsets.UTF_8
        );
        BufferedReader buffer = new BufferedReader(inputStreamReader);

        String line = "";
        try {
            while ( (line = buffer.readLine()) != null ){
                if (line.equals("")) continue;

                String[] data = line.split(",");

                String eventName = data[0].trim();
                String teamName = data[1].trim();
                String activity = data[2].trim();
                String time = data[3].trim();
                String date = data[4].trim();
                String status = data[5].trim();


                scheduleList.addActivity(new Schedule(eventName, teamName, activity, time, date,status));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return scheduleList;
    }

    @Override
    public void writeData(ScheduleList data) {
        BufferedWriter buffer = null;
        FileOutputStream fileOutputStream;
        File file = new File(fileName);
        try {
            fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    fileOutputStream,StandardCharsets.UTF_8);
            buffer = new BufferedWriter(outputStreamWriter);

            for (Schedule schedule : data.getActivityList()){
                String line = schedule.getEventName()+","
                        + schedule.getTeamName()+","
                        + schedule.getActivity()+","
                        + schedule.getTime()+","
                        + schedule.getDate()+","
                        + schedule.getStatus();

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