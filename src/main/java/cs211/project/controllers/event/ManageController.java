package cs211.project.controllers.event;

import cs211.project.models.event.Event;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class ManageController {
    @FXML Label nameLabel;

    @FXML
    public void clickBackToCreate() throws IOException {
        FXRouter.goTo("create");
    }
    @FXML
    public void ClickToGoMoreMange()throws IOException {
        FXRouter.goTo("manageInfo");
    }
    @FXML
    public void ClickToGoManu()throws IOException {
        FXRouter.goTo("main");
    }

    @FXML
    private void initialize(){
        Event event = (Event) FXRouter.getData();
        nameLabel.setText(event.getName());
    }

}
