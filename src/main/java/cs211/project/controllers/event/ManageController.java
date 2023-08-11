package cs211.project.controllers.event;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class ManageController {
    @FXML
    public void clickBackToCreate() throws IOException {
        FXRouter.goTo("create");
    }
}
