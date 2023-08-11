package cs211.project.controllers.main;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class JoinHistoryController {

    @FXML
    private void onBackButtonClick() throws IOException {
        FXRouter.goTo("profile");
    }
}
