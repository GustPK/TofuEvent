package cs211.project.services;

import cs211.project.models.Team;
import cs211.project.models.collections.TeamList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TeamListDatasource implements Datasource<TeamList>{

    private String directoryName;
    private String fileName;

    public TeamListDatasource(String directoryName, String fileName) {
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
    public TeamList readData() {
        TeamList teams = new TeamList();
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
                String joinFieldText = data[2].trim();


                teams.addTeam(new Team(eventName, teamName, joinFieldText));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return teams;
    }

    @Override
    public void writeData(TeamList data) {
        BufferedWriter buffer = null;
        FileOutputStream fileOutputStream;
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        try {
            fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    fileOutputStream, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(outputStreamWriter);

            for (Team team : data.getTeams()) {
                String line = team.getEventName() + ","
                        + team.getTeamName() + ","
                        + team.getJoinFieldText();

                buffer.append(line);
                buffer.append("\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.flush();
                buffer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
