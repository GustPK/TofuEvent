package cs211.project.controllers.login;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class DevController {
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView PImageView;
    @FXML
    private ImageView BImageView;
    @FXML
    private ImageView GImageView;
    @FXML
    private ImageView NImageView;
    @FXML
    void BackButton() throws IOException {
        String viewPath = "ku/cs/views";
        FXRouter.goTo("login");
    }
//    @FXML
//    public void initialize() {
//        Image image = new Image(getClass().getResourceAsStream("/images/ชื่อไฟล์"));
//        PImageView.setImage(image);
//    }
}