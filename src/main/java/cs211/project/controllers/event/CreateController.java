package cs211.project.controllers.event;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class CreateController {
    @FXML
    public void clickBackToMain() throws IOException {
        FXRouter.goTo("main");
    }
    @FXML
    public void clickToManage() throws IOException {
        FXRouter.goTo("manage");
    }
}
