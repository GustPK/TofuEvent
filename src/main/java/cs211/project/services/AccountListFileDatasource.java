package cs211.project.services;

import cs211.project.model.AccountList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class AccountListFileDatasource implements Datasource<AccountList> {
    private String directoryName; // You need to declare these variables
    private String fileName;

    public AccountListFileDatasource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
    }

    public AccountList readData() {
        AccountList admins = new AccountList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        // Prepare objects for reading the file
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
            while ((line = buffer.readLine()) != null) {
                if (line.equals("")) continue;

                String[] data = line.split(",");

                String user = data[0].trim();
                String pass = data[1].trim(); // Use index 1 for password

                admins.addAdmin(user, pass);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace(); // Proper error handling
            }
        }

        return admins;
    }

    @Override
    public void writeData(AccountList data) {

    }
}
