package cs211.project.controllers.login;
import cs211.project.models.account.LoggedInAccount;

import cs211.project.models.account.Account;

import cs211.project.models.collections.AccountList;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginController {

    @FXML private TextField usernameTextField;

    @FXML private PasswordField passwordField;

    @FXML Label warning;
    private AccountList currentAccount;
    private AccountList accountsList;
    private Datasource<AccountList> accountListDataSource;


    public void showAlert(String title, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void initialize(){
        warning.setText("");
        accountListDataSource = new AccountListDatasource();
        accountsList = accountListDataSource.readData();
        currentAccount = new AccountList();
    }

    public void clickLogin() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        Account exist = accountsList.checkLogin(username, password);

        if (exist != null) {
            if (!exist.isBanned()) {
                System.out.println("Login: Success");
                LoggedInAccount.getInstance().setAccount(exist);

                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedTime = currentTime.format(formatter);

                exist.setOnline(formattedTime);

                accountListDataSource.writeData(accountsList);

                if (exist.isAdmin()) {
                    FXRouter.goTo("admin");
                } else {
                    FXRouter.goTo("main");
                    String scene = "main";
                    try {
                        FXRouter.goTo(scene, accountsList.getAccounts());
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า main ไม่ได้" + e);
                    }
                }
            } else {
                showAlert("Login Failed", "You got banned", AlertType.WARNING);
                System.out.println("Login: User is banned");
            }
        } else {
            showAlert("Login Failed", "Username or password is wrong", AlertType.ERROR);
            System.out.println("Login: Failed");
        }
    }

    @FXML
    private void onDevelopersHyperlinkClick() throws IOException{
        FXRouter.goTo("developer");
    }

    @FXML
    private void onCreateAccountHyperlinkClick() throws IOException{
        FXRouter.goTo("register");
    }

    @FXML
    private void onInstructionsHyperlinkClick() throws IOException{
        FXRouter.goTo("instruction");
    }

}