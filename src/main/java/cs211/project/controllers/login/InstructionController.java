package cs211.project.controllers.login;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import java.io.IOException;

public class InstructionController {

    @FXML
    void BackButton() throws IOException {
        String viewPath = "ku/cs/views";
        FXRouter.goTo("login");
    }

}
