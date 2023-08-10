package cs211.project.controllers.login;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button Login;
    @FXML
    private TextField Username;
    @FXML
    private PasswordField Password;
    @FXML
    private Hyperlink CreateAccount;
    @FXML
    private Hyperlink ForgetPassword;
    @FXML
    private Hyperlink Developers;

    @FXML
    private void onDevelopersHyperlinkClick() throws IOException{
        FXRouter.goTo("developer");
    }

    @FXML
    private void onCreateAccountHyperlinkClick() throws IOException{
        FXRouter.goTo("register");
    }

    @FXML
    private void onLoginButtonClick() throws IOException{
        FXRouter.goTo("main");
    }

}