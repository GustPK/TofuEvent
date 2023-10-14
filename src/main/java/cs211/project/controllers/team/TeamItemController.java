package cs211.project.controllers.team;

import cs211.project.models.account.Account;
import cs211.project.models.collections.EventList;
import cs211.project.models.event.Team;
import cs211.project.models.event.Event;
import cs211.project.services.AccountListDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.io.IOException;

public class TeamItemController {
    private Event selectedEvent;
    @FXML
    private Label eventNameLabel;

    @FXML
    private Label teamName;
    @FXML
    private ImageView eventImg;
    private Event eventTamp;
    private Team team;

    private Datasource<EventList> eventListDatasource;
    private EventList eventList;


    public void setData(Team team){
        eventListDatasource = new EventListDatasource();
        eventList = eventListDatasource.readData();
        for(Event event : eventList.getEvents())
            if (event.getName().equals(team.getEventName()))
                eventTamp = event;

        String imagePath = "data/images/" + eventTamp.getImgEvent();
        File imageFile = new File(imagePath);
        Image profileImage = new Image(imageFile.toURI().toString());
        eventImg.setImage(profileImage);
        this.team = team;
        eventNameLabel.setText(team.getEventName());
        teamName.setText(team.getTeamName());


    }

    @FXML
    private void onEventItemClick() throws IOException {
        FXRouter.goTo("team", team);
    }


}
