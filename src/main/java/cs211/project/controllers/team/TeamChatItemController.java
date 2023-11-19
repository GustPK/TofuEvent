package cs211.project.controllers.team;

import cs211.project.models.collections.EventList;
import cs211.project.models.event.Team;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class TeamChatItemController {

    @FXML
    private Label eventNameLabel;
    @FXML
    private Label teamName;
    @FXML
    private ImageView eventImg;
    private Team team;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;

    public void setData(Team team){
        eventListDatasource = new EventListDatasource();
        eventList = eventListDatasource.readData();
        String imagePath = "data/images/" + eventList.findByEventName(team.getEventName()).getImgEvent();
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