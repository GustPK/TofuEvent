package cs211.project.services;

import cs211.project.models.ActivityList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ActivityFileDatasource implements Datasource<ActivityList> {
    private String directoryName;
    private String fileName;

    public ActivityFileDatasource(String directoryName, String fileName) {
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
    public ActivityList readData() {
        ActivityList activityList = new ActivityList();
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


                String teamName = data[0].trim();
                String time = data[1].trim();
                String activity = data[2].trim();
                String eventName = data[3].trim();

                activityList.addActivity(teamName, time, activity, eventName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return activityList;
    }

    @Override
    public void writeData(ActivityList data) {

    }
}
