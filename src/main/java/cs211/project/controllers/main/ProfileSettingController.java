package cs211.project.controllers.main;

import cs211.project.models.account.Account;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.AccountList;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class ProfileSettingController {
    @FXML
    private Label accountName;
    @FXML
    private Label userName;
    @FXML
    private Circle profilePic;
    @FXML
    private TextField accountNameChangeField;
    @FXML
    private Account currentAccount;
    private AccountList accountList;
    private Datasource <AccountList> datasource;
    @FXML
    private void initialize(){
        currentAccount = (Account) FXRouter.getData();
        Image image = new Image(getClass().getResource("/images/"+ LoggedInAccount.getInstance().getAccount().getImage()).toString());
        profilePic.setFill(new ImagePattern(image));
        accountName.setText(LoggedInAccount.getInstance().getAccount().getName());
        userName.setText(LoggedInAccount.getInstance().getAccount().getUsername());
        datasource = new AccountListDatasource("data","Account.csv");
        accountList = datasource.readData();
    }
    @FXML
    private void onBackButtonClick() throws IOException {
        FXRouter.goTo("profile");
    }

}
