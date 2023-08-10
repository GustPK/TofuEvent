package cs211.project.controllers.main;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class MainController {

    @FXML
    private Circle profilePic;
    @FXML
    private void initialize(){
        Image navyImage = new Image(getClass().getResource("/images/navy-default-profile.jpg").toString());
        profilePic.setFill(new ImagePattern(navyImage));
    }
    @FXML
    protected void onProfileButtonClick() throws IOException{
        FXRouter.goTo("profile");
    }

    @FXML
    private void onThisEventAsstaffButtonClick() throws IOException{
        FXRouter.goTo("team");
    }

    @FXML
    private void onCreateEventButtonClick() throws IOException{
        FXRouter.goTo("createEvent");
    }

    @FXML
    private void onOngoingEventsButtonClick() throws IOException{
        FXRouter.goTo("ongoingEvents");
    }

    @FXML
    private void onEventInfoButtonClick() throws IOException{
        FXRouter.goTo("eventInfo");
    }
}
