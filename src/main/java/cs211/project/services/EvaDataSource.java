package cs211.project.services;

import cs211.project.models.account.Account;
import cs211.project.models.account.AccountList;
import cs211.project.models.event.Eva;
import cs211.project.models.event.EvaList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class EvaDataSource implements DataSource<EvaList>{
    private String fileName = "data" + File.separator + "Eva.csv";

    public EvaDataSource() {
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
    public EvaList readData() {
        BufferedReader buffer = null;
        FileInputStream fileInputStream = null;

        EvaList events = new EvaList() ;
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

                String name = data[0].trim();
                String date = data[1].trim();

                events.addEvent((new Eva(name,date)));

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
    public void writeData(EvaList data) {

    }

}
