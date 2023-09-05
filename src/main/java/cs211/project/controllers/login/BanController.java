package cs211.project.controllers.login;

import cs211.project.model.Account;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class BanController {
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Label statusLabel;
    private Account acc;
    @FXML
    private void initialize() {
        acc = (Account) FXRouter.getData();
        usernameLabel.setText(acc.getUser());
        passwordLabel.setText(acc.getPass());
    }

    @FXML
    private void onBack() throws IOException {
        FXRouter.goTo("admin");
    }

    @FXML
    private void onBan() {
        ;
    }
}
