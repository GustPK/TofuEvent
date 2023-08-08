package cs211.project.controllers.main;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class MainControllers {

    @FXML
    private Circle profilePic;
    @FXML
    private void initialize(){
        Image image = new Image(getClass().getResource("/images/pink-default-profile.jpg").toString());
        profilePic.setFill(new ImagePattern(image));
    }
    @FXML
    protected void onProfileButtonClick() throws IOException{
        FXRouter.goTo("profile");
    }
}
