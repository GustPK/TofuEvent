package cs211.project.controllers.creator;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class StaffManageController {
    @FXML
    void clickToShowSample () throws IOException {
        FXRouter.goTo("staffSchedule");
    }
}
