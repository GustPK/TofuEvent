package cs211.project.controllers.main;

import cs211.project.models.account.Account;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;

public class ProfileSettingController {
    @FXML
    private Label accountName;
    @FXML
    private Label userName;
    @FXML
    private Circle profilePic;
    @FXML
    private Account currentAccount;
    @FXML
    private void initialize(){
        currentAccount = (Account) FXRouter.getData();
        File file = new File("data/images", LoggedInAccount.getInstance().getAccount().getImage());
        String path = "file:///" + file.getAbsolutePath();
        Image image = new Image(path);
        profilePic.setFill(new ImagePattern(image));
        accountName.setText(LoggedInAccount.getInstance().getAccount().getName());
        userName.setText(LoggedInAccount.getInstance().getAccount().getUsername());
    }
    @FXML
    private void onBackButtonClick() throws IOException {
        FXRouter.goTo("profile");
    }
}
