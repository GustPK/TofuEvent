package cs211.project.controllers.event;

import cs211.project.models.event.Event;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.File;

public class Des {
    @FXML
    private Label date;
    @FXML private TextFlow nameField;
    @FXML private Rectangle imageEvent;
    @FXML
    ComboBox selectTeam;
    @FXML
    private void initialize(){
        Event event = (Event) FXRouter.getData();
        Text text = new Text(event.getName());
        text.setFill(Color.WHITE);

        nameField.getChildren().add(text);

        date.setText("29");
        String imagePath = "data/images/" + event.getImgEvent();
        File imageFile = new File(imagePath);
        Image profileImage = new Image(imageFile.toURI().toString());
        imageEvent.setFill(new ImagePattern(profileImage));

    }


}
