package cs211.project.services;

import cs211.project.models.event.Comment;
import cs211.project.models.collections.CommentList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CommentListDatasource implements Datasource<CommentList>{
    private String fileName = "data" + File.separator + "comment.csv";

    public CommentListDatasource() { checkFileIsExisted(); }

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
    public CommentList readData() {
        CommentList comments = new CommentList();
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

                String username = data[0].trim();
                String teamName = data[1].trim();
                String comment = data[2].trim();
                String eventName = data[3].trim();


                comments.addComment(teamName, comment, eventName,username);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return comments;
    }

    @Override
    public void writeData(CommentList data) {
        File file = new File(fileName);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(outputStreamWriter)) {

            for (Comment comment : data.getCommentList()) {
                String username = comment.getUsername();
                String teamName = comment.getTeamName();
                String commentText = comment.getComment();
                String eventName = comment.getEventName();

                String csvLine = username + ","+ teamName + "," + commentText + "," + eventName;
                writer.write(csvLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while writing data to file: " + e.getMessage(), e);
        }
    }

}
