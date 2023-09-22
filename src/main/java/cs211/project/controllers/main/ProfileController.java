package cs211.project.controllers.main;

import cs211.project.models.account.Account;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.AccountList;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ProfileController {

    @FXML
    private Label accountName;
    @FXML
    private Label username;
    @FXML
    private Circle profilePic;
    private AccountList currentAccount;

    @FXML
    private void initialize(){
        currentAccount = (AccountList) FXRouter.getData();
//        Image image = new Image(getClass().getResource("data/images/" + LoggedInAccount.getInstance().getAccount().getImage()).toString());
        File file = new File("data/images", LoggedInAccount.getInstance().getAccount().getImage());
        String path = "file:///" + file.getAbsolutePath();
        Image image = new Image(path);
        profilePic.setFill(new ImagePattern(image));
        accountName.setText(LoggedInAccount.getInstance().getAccount().getName());
        username.setText(LoggedInAccount.getInstance().getAccount().getUsername());

    }
    @FXML
    protected void onProfileSettingClick() throws IOException{
        FXRouter.goTo("profileSetting");
    }
    @FXML
    protected void onBackButtonClick() throws IOException {
        FXRouter.goTo("main");
    }
    @FXML
    protected void onLogoutButtonClick() throws IOException {
        FXRouter.goTo("login");
    }

    @FXML
    private void onJoinedHistoryButtonClick() throws IOException{
        FXRouter.goTo("joinHistory");
    }
}
