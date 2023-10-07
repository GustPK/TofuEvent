package cs211.project.controllers.login;

import cs211.project.models.account.Account;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.AccountList;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ChangePasswordController {
    @FXML
    private TextField currentPasswordField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private Label warning;
    private AccountList accountList;
    private Datasource <AccountList> datasource;
    private String lastPage;
    @FXML
    private void initialize(){
        warning.setText("");
        lastPage = (String) FXRouter.getData();
        datasource = new AccountListDatasource();
        accountList = datasource.readData();
    }
    @FXML
    private void onAdminButtonClick() throws IOException {
        if (lastPage.equals("fromAdmin")) {
            FXRouter.goTo("admin");
        }
        if (lastPage.equals("fromPSC")){
            FXRouter.goTo("profileSetting");
        }
    }
    @FXML
    private void confirmPassword() {
        Account currentLoggedInAccount = LoggedInAccount.getInstance().getAccount();
        if (currentLoggedInAccount.getPassword().equals(currentPasswordField.getText())){
            if (newPasswordField.getText().equals(confirmPasswordField.getText())){
                Account existAccount = accountList.findByUsername(currentLoggedInAccount.getUsername());
                if (existAccount != null){
                    existAccount.setPassword(newPasswordField.getText());
                    datasource.writeData(accountList);
                    currentPasswordField.clear();
                    newPasswordField.clear();
                    confirmPasswordField.clear();
                    warning.getStyleClass().add("green-text");
                    warning.setText("Password changed successfully");
                }
            }
            else {
                warning.getStyleClass().add("red-text");
                warning.setText("password are not matching");
            }
        }
        else {
            warning.getStyleClass().add("red-text");
            warning.setText("invalid password");
        }
    }
}
