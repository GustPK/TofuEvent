package cs211.project.controllers.main;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class ProfileController {
    @FXML
    private Label accountName;
    @FXML
    private Label username;
    @FXML
    private Circle profilePic;
    @FXML
    private void initialize(){
        Image navyImage = new Image(getClass().getResource("/images/navy-default-profile.jpg").toString());
        profilePic.setFill(new ImagePattern(navyImage));
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
