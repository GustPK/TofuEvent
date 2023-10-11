package cs211.project.controllers.team;

import cs211.project.models.Activity;
import cs211.project.models.ActivityList;
import cs211.project.models.Comment;
import cs211.project.models.Team;
import cs211.project.services.ActivityFileDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class TeamController {
    private Datasource<ActivityList> activityListDatasource;
    private ActivityList activityList;
    @FXML private TableView<Activity> activityTableView;
    private Team currentTeam;
    @FXML
    private ListView<Comment> comment;
    @FXML
    private TextField commentField;

    @FXML
    public void clickBackToMyTeam() throws IOException {
        FXRouter.goTo("myteam");
    }

    @FXML
    public void onClickComment(){
        String c = commentField.getText();
        Comment comment = new Comment(currentTeam.getTeamName(), c, currentTeam.getEventName());
        this.comment.getItems().add(comment);
    }
    @FXML
    public void initialize(){
        currentTeam = (Team) FXRouter.getData();
        activityListDatasource = new ActivityFileDatasource("data", "Activity.csv");
        activityList = activityListDatasource.readData();

        showActivity();
    }

    public void showActivity() {
        activityTableView.getColumns().clear();
        activityTableView.getItems().clear();
        TableColumn<Activity, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Activity, String> activityColumn = new TableColumn<>("Activity");
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));

        activityTableView.getColumns().addAll(timeColumn, activityColumn);

        for (Activity activity : activityList.getActivityList()) {
            if (activity.getTeamName().equals(currentTeam.getTeamName()) && activity.getEventName().equals(currentTeam.getEventName())) {
                activityTableView.getItems().add(activity);
            }
        }
    }

}
