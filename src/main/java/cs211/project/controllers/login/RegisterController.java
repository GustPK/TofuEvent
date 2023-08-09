package cs211.project.controllers.login;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class RegisterController {
    @FXML
    public void clickBackToLogin() throws IOException {
        FXRouter.goTo("login"); 
    }

}
