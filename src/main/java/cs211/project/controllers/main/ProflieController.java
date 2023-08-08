package cs211.project.controllers.main;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class ProflieController {
    @FXML
    protected void onDeveloperClick() throws IOException{
        FXRouter.goTo("");
    }
    @FXML
    protected void onBackButtonClick() throws IOException {
        FXRouter.goTo("main");
    }
}
