package cs211.project.controllers.creator;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class ManageInfoController {

    @FXML
    void ClickToGoManu() throws IOException {
        FXRouter.goTo("main");
    }
    @FXML
    void ClickGoToTeamSetting() throws IOException {
        FXRouter.goTo("staffMange");
    }
}
