package cs211.project.controllers.team;

import cs211.project.services.FXRouter;
import javafx.fxml.FXML;

import java.io.IOException;

public class TeamController {
    @FXML
    private void onTeamChatHyperlinkClick() throws IOException {
        FXRouter.goTo("teamChat");
    }

    @FXML
    public void clickBackToMain() throws IOException {
        FXRouter.goTo("main");
    }
}
