package cs211.project.controllers.event;

import cs211.project.models.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EventItemController {

    private Event selectedEvent;
    @FXML
    private Label eventNameLabel;

    @FXML
    private ImageView eventIMG;
    private Event event;

    public void setData(Event event){
        this.event = event;
        eventNameLabel.setText(event.getName());
        Image image = new Image(event.getImgSrc());
        eventIMG.setImage(image);

    }

    @FXML
    private void onEventItemClick(){
        selectedEvent = this.event;
    }

}
