package cs211.project.controllers.event;

import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.event.Event;

import cs211.project.models.event.Participant;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EventItemController {

    private Event selectedEvent;
    @FXML
    private Label eventNameLabel;
    @FXML
    private Label eventDate;
    @FXML
    private Label eventDateEnd;
    @FXML
    private ImageView eventImg;
    private Event event;


    public void setData(Event event) {
        this.event = event;
        eventNameLabel.setText(event.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate ds = LocalDate.parse(event.getDateStart(), formatter);
        String formattedDateStart = ds.format(DateTimeFormatter.ofPattern("dd MMM"));
        LocalDate de = LocalDate.parse(event.getDateEnd(), formatter);
        String formattedDateEnd = de.format(DateTimeFormatter.ofPattern("dd MMM"));
        eventDate.setText(formattedDateStart+ "-");
        eventDateEnd.setText(formattedDateEnd);

        String path = "data/images/" + event.getImgEvent();
        File file = new File(path);
        Image image = new Image(file.toURI().toString());
        eventImg.setImage(image);
    }


    @FXML
    private void onEventItemClick(){
        selectedEvent = this.event;
    }

}
