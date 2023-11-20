package cs211.project.controllers.team;

import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.ParticipantList;
import cs211.project.models.collections.TeamList;
import cs211.project.models.event.Participant;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MyTeamController {
    private ParticipantList participantList;
    private Datasource<ParticipantList> participantListDatasource;
    private Datasource<TeamList> teamListDatasource;
    private TeamList teamList;
    @FXML
    private GridPane grid;

    @FXML
    public void clickBackToMain() throws IOException {
        FXRouter.goTo("main");
    }

    @FXML
    public void initialize() throws IOException {
        participantListDatasource = new ParticipantListDatasource();
        teamListDatasource = new TeamListDatasource();
        participantList = participantListDatasource.readData();
        teamList = teamListDatasource.readData();
        int column = 1;
        int row = 0;

        try {
            for (Participant participant : participantList.getParticipants()) {
                if (participant.checkName(LoggedInAccount.getInstance().getAccount().getUsername())) {
                    if (!participant.checkTeamName("join")) {
                        if (!participant.isBanned()) {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/team-item-view.fxml"));
                            AnchorPane anchorPane = fxmlLoader.load();

                            TeamChatItemController teamItemController = fxmlLoader.getController();
                            teamItemController.setData(teamList.findByTeamName(participant.getTeamName()));

                            grid.add(anchorPane, column, row++);
                            GridPane.setMargin(anchorPane, new Insets(10));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}