package cs211.project.controllers.event;

import cs211.project.models.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EventItemController {

    private Event selectedEvent;
    @FXML
    private Label eventNameLabel;
    @FXML
    private Label eventDate;

    @FXML
    private ImageView eventImg;
    private Event event;

    public void setData(Event event){
        this.event = event;
        eventNameLabel.setText(event.getName());
        Image image = new Image(event.getImgEvent());
        eventImg.setImage(image);
        eventDate.setText(event.getDate());
    }

    @FXML
    private void onEventItemClick(){
        selectedEvent = this.event;
    }

}
