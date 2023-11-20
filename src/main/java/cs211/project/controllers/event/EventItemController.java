package cs211.project.controllers.event;

import cs211.project.models.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class EventItemController {
    @FXML
    private Label eventNameLabel;
    @FXML
    private Label eventDate;
    @FXML
    private ImageView eventImg;
    @FXML
    private Label count;

    public void setData(Event event){
        eventNameLabel.setText(event.getName());
        eventDate.setText(event.getDateStart());
        String path = "data/images/"+event.getImgEvent();
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        eventImg.setImage(image);

        int maximum = Integer.parseInt(event.getMaximum());
        int joined = Integer.parseInt(event.getJoinedText());

        if (maximum == joined) {
            count.setText("FULL");
            count.setStyle("-fx-background-color: RED; -fx-background-radius: 20;");
        }else
            count.setText(joined +"/"+maximum);
    }



}
