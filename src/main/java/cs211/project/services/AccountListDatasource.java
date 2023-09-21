package cs211.project.services;

import cs211.project.models.account.Account;
import cs211.project.models.collections.AccountList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class AccountListDatasource implements Datasource<AccountList> {

    private String fileName = "data" + File.separator + "Account.csv";
    public AccountListDatasource() {
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
    public AccountList readData() {
        BufferedReader buffer;
        FileInputStream fileInputStream ;
        AccountList accounts = new AccountList() ;
        File file = new File(fileName);
        try {
            fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(
                    fileInputStream,StandardCharsets.UTF_8);
            buffer = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = buffer.readLine()) != null){
                if (line.equals("")) continue;
                String[] data = line.split(",");

                String name = data[0].trim();
                String userName = data[1].trim();
                String password = data[2].trim();
                String image = data[3].trim();
                String status = data[4].trim();

                accounts.addAccount((new Account(name,userName,password,image,status)));

            }
            buffer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return accounts;
    }

    @Override
    public void writeData(AccountList data) {
        BufferedWriter buffer = null;
        FileOutputStream fileOutputStream;
        File file = new File(fileName);
        try {
            fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    fileOutputStream,StandardCharsets.UTF_8);
            buffer = new BufferedWriter(outputStreamWriter);

            for (Account account : data.getAccounts()){
                String line = account.getName()+","
                        + account.getUsername()+","
                        + account.getPassword()+","
                        + account.getImage()+","
                        + account.getStatus();

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