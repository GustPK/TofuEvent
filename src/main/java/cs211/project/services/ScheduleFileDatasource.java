package cs211.project.services;

import cs211.project.models.Schedule;
import cs211.project.models.ScheduleList;
import cs211.project.models.event.Event;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ScheduleFileDatasource implements Datasource<ScheduleList> {
    private String directoryName;
    private String fileName;

    public ScheduleFileDatasource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public ScheduleList readData() {
        ScheduleList scheduleList = new ScheduleList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

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

                scheduleList.addActivity(new Schedule(eventName, teamName, activity, time, date));
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
        File file = new File("data" + File.separator + "schedule.csv");
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
                        + schedule.getDate();

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
