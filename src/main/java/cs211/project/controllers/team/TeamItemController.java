package cs211.project.controllers.team;

import cs211.project.models.event.Team;
import cs211.project.models.event.Event;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class TeamItemController {
    private Event selectedEvent;
    @FXML
    private Label eventNameLabel;

    @FXML
    private Label teamName;
    @FXML
    private ImageView eventImg;
    private Event event;
    private Team team;


    public void setData(Team team){
        this.team = team;
        eventNameLabel.setText(team.getEventName());
        teamName.setText(team.getTeamName());


    }

    @FXML
    private void onEventItemClick() throws IOException {
        FXRouter.goTo("team", team);
    }


}
