package cs211.project.controllers.team;

import cs211.project.model.Team;
import cs211.project.model.TeamList;
import cs211.project.services.FXRouter;
import cs211.project.services.TeamHardCodeDatasource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TeamController {

    @FXML
    public void clickBackToMain() throws IOException {
        FXRouter.goTo("main");
    }


    @FXML private ListView<Team> teamListView;
    private TeamList teamList;

    @FXML
    public void initialize() {
        TeamHardCodeDatasource datasource = new TeamHardCodeDatasource();
        teamList = datasource.readData();
        showList(teamList);

        teamListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Team>() {
            @Override
            public void changed(ObservableValue observable, Team oldValue, Team newValue) {
                if (newValue != null) {
                    try {
                        // FXRouter.goTo สามารถส่งข้อมูลไปยังหน้าที่ต้องการได้ โดยกำหนดเป็น parameter ที่สอง
                        FXRouter.goTo("team",newValue);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


    }

    private void showList(TeamList teamList) {
        teamListView.getItems().addAll(teamList.getTeams());
    }

    @FXML
    private void goToTeam() throws IOException{
        FXRouter.goTo("team-view");
    }


}
