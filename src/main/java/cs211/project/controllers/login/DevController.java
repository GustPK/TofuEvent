package cs211.project.controllers.login;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class DevController {

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
    @FXML
    public void initialize() {
        String personImagePath = "file:data/images/P1x1.jpg";
        Image personImage = new Image(personImagePath);
        PImageView.setImage(personImage);

        String person2ImagePath = "file:data/images/B1x1.jpg";
        Image person2Image = new Image(person2ImagePath);
        BImageView.setImage(person2Image);

        String person3ImagePath = "file:data/images/G1x1.jpg";
        Image person3Image = new Image(person3ImagePath);
        GImageView.setImage(person3Image);

        String person4ImagePath = "file:data/images/N1x1.jpg";
        Image person4Image = new Image(person4ImagePath);
        NImageView.setImage(person4Image);
    }
}