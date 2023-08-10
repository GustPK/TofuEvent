package cs211.project.controllers.login;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class ChangePasswordController {
    @FXML
    private void onAdminButtonClick() throws IOException {
        FXRouter.goTo("admin");
    }
}
