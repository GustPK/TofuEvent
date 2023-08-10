package cs211.project.controllers.main;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class ProfileController {

    @FXML
    private Circle profilePic;
    @FXML
    private void initialize(){
        Image navyImage = new Image(getClass().getResource("/images/navy-default-profile.jpg").toString());
        profilePic.setFill(new ImagePattern(navyImage));
    }

    @FXML
    protected void onDeveloperClick() throws IOException{
        FXRouter.goTo("");
    }
    @FXML
    protected void onBackButtonClick() throws IOException {
        FXRouter.goTo("main");
    }
    @FXML
    protected void onLogoutButtonClick() throws IOException {
        FXRouter.goTo("login");
    }
}
