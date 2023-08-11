package cs211.project.controllers.managment;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class ManageController {

    @FXML
    void ClickToGoManu() throws IOException {
        FXRouter.goTo("main");
    }
}
