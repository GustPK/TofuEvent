package cs211.project.services;

import cs211.project.models.event.Team;
import cs211.project.models.collections.TeamList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TeamListDatasource implements Datasource<TeamList>{

    private String fileName = "data" + File.separator + "TeamList.csv";

    public TeamListDatasource() {checkFileIsExisted(); }

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
    public TeamList readData() {
        TeamList teams = new TeamList();
//        String filePath = directoryName + File.separator + fileName;
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
                String joinFieldText = data[2].trim();
                String joinedText = data[3].trim();


                teams.addTeam(new Team(eventName, teamName, joinFieldText, joinedText));
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
        File file = new File(fileName);

        try {
            fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    fileOutputStream, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(outputStreamWriter);

            for (Team team : data.getTeams()) {
                String line = team.getEventName() + ","
                        + team.getTeamName() + ","
                        + team.getJoinFieldText() + ","
                        + team.getJoinedText();

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
