package cs211.project.services;

import cs211.project.models.CommentList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CommentListDatasource implements Datasource<CommentList>{


    private String directoryName;
    private String fileName;

    public CommentListDatasource(String directoryName, String fileName) {
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
    public CommentList readData() {
        CommentList comments = new CommentList();
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
                String comment = data[1].trim();
                String eventName = data[2].trim();


                comments.addComment(teamName, comment, eventName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return comments;
    }

    @Override
    public void writeData(CommentList data) {

    }
}
