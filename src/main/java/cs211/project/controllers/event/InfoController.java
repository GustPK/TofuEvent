package cs211.project.controllers.event;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class InfoController {

    @FXML
    public void clickBackToMain() throws IOException {
        FXRouter.goTo("main");
    }



}
