package cs211.project.controllers.login;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

import java.io.IOException;

public class AdminController {
    @FXML
    private Hyperlink changepassword;

    @FXML
    private void onChangePasswordHyperlinkClick() throws IOException {
        FXRouter.goTo("changePassword");
    }
    @FXML
    void BackButton() throws IOException {
        String viewPath = "ku/cs/views";
        FXRouter.goTo("login");
    }

}