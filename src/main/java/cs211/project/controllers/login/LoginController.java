package cs211.project.controllers.login;

import cs211.project.models.account.Account;
import cs211.project.models.account.AccountList;
import cs211.project.services.AccountDataSource;
import cs211.project.services.DataSource;
import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button login;
    @FXML
    private Hyperlink createAccount;
    @FXML
    private Hyperlink forgetPassword;
    @FXML
    private Hyperlink developers;

    private AccountList accountsList;
    private DataSource<AccountList> accountListDataSource;


    @FXML
    public void initialize(){
//        accountsList = new AccountList();
//        accountListDataSource = new AccountDataSource();
//        accountsList = accountListDataSource.getData();
        accountListDataSource = new AccountDataSource();
        accountsList = accountListDataSource.readData();
    }

    @FXML
    public void clickLoin() throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if (accountsList.checkLogin(username,password)){
            System.out.println("Login: Success");
            FXRouter.goTo("main");
        }
        else{
            System.out.println("Login: มึงมั่วละไอ้เวร");
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

//    @FXML
//    private void onLoginButtonClick() throws IOException{
//        FXRouter.goTo("main");
//    }

    @FXML
    private void onAdminHyperlinkClick() throws IOException {
        FXRouter.goTo("admin");
    }

}