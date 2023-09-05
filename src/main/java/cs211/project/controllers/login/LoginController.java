package cs211.project.controllers.login;

import cs211.project.models.collections.AccountList;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    @FXML
    Label warning;

    private AccountList accountsList;
    private Datasource<AccountList> accountListDataSource;


    @FXML
    public void initialize(){
        warning.setText("");
//        accountsList = new AccountList();
//        accountListDataSource = new AccountDataSource();
//        accountsList = accountListDataSource.getData();
        accountListDataSource = new AccountListDatasource();
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
            warning.setText("username or password is wrong");
            System.out.println("Login: failed");
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