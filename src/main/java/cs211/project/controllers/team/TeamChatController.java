package cs211.project.controllers.team;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class TeamChatController {
    @FXML
    public void clickBackToTeam() throws IOException {
        FXRouter.goTo("team");
    }
}
