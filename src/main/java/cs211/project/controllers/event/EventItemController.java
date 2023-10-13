package cs211.project.controllers.event;

import cs211.project.models.Team;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.event.Event;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class EventItemController {

    private Event selectedEvent;
    @FXML
    private Label eventNameLabel;
    @FXML
    private Label eventDate;
    @FXML
    private ImageView eventImg;
    private Event event;
    @FXML
    private Label count;

    public void setData(Event event){
        this.event = event;
        eventNameLabel.setText(event.getName());
        eventDate.setText(event.getDateStart());
        String path = "data/images/"+event.getImgEvent();
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        eventImg.setImage(image);

        int data7 = Integer.parseInt(event.getJoinFieldText()); // Assuming data[7] is an integer in the CSV
        int data8 = Integer.parseInt(event.getJoinedText()); // Assuming data[8] is an integer in the CSV
        int countValue = data7 - data8;

        // Convert the count value to a String and set it to the label
        count.setText(countValue +"/"+data7);
    }

    @FXML
    private void onEventItemClick(){
        selectedEvent = this.event;
    }

}
