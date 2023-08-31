package cs211.project.controllers.event;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.shape.Circle;


import java.io.IOException;

public class InfoController {

    private Circle profilePic;

    @FXML
    protected void onProfileButtonClick() throws IOException{
        FXRouter.goTo("profile");
    }

    @FXML
    private void onThisEventAsStaffButtonClick() throws IOException{
        FXRouter.goTo("team");
    }

    @FXML
    private void onCreateEventButtonClick() throws IOException{
        FXRouter.goTo("create");
    }

    @FXML
    private void onOngoingEventsButtonClick() throws IOException{
        FXRouter.goTo("ongoing");
    }


    @FXML
    private void onEventInfoButtonClick() throws IOException{
        FXRouter.goTo("info");
    }



}
