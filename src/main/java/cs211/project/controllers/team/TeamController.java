package cs211.project.controllers.team;

import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.CommentList;
import cs211.project.models.collections.ScheduleList;
import cs211.project.models.event.Comment;
import cs211.project.models.event.Schedule;
import cs211.project.models.event.Team;
import cs211.project.services.CommentListDatasource;
import cs211.project.services.ScheduleFileDatasource;
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
    private Datasource<ScheduleList> activityListDatasource;
    private ScheduleList scheduleList;
    @FXML private TableView<Schedule> activityTableView;
    private Team currentTeam;
    @FXML
    private ListView<Comment> comment;
    @FXML
    private TextField commentField;
    private Datasource<CommentList> commentListDatasource;
    private CommentList commentList;

    @FXML
    public void clickBackToMyTeam() throws IOException {
        FXRouter.goTo("myteam");
    }

    @FXML
    public void onClickComment(){
        String commentFieldText = commentField.getText();
        Comment comment = new Comment(currentTeam.getTeamName(), commentFieldText, currentTeam.getEventName(),LoggedInAccount.getInstance().getAccount().getUsername());
        this.comment.getItems().add(comment);
        commentList.addComment(currentTeam.getTeamName(), commentFieldText, currentTeam.getEventName(),LoggedInAccount.getInstance().getAccount().getUsername());
        commentListDatasource.writeData(commentList);
        commentField.clear();
    }
    @FXML
    public void initialize(){
        currentTeam = (Team) FXRouter.getData();
        activityListDatasource = new ScheduleFileDatasource();
        scheduleList = activityListDatasource.readData();
        commentListDatasource = new CommentListDatasource();
        commentList = commentListDatasource.readData();
        showComment(commentList);

        showActivity();
    }

    public void showActivity() {
        activityTableView.getColumns().clear();
        activityTableView.getItems().clear();
        TableColumn<Schedule, String> dateColumn = new TableColumn<>("DATE");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setMinWidth(150);

        TableColumn<Schedule, String> timeColumn = new TableColumn<>("TIME");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeColumn.setMinWidth(150);

        TableColumn<Schedule, String> activityColumn = new TableColumn<>("ACTIVITY");
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        activityColumn.setMinWidth(150);

        activityTableView.getColumns().addAll(dateColumn, timeColumn, activityColumn);
        activityTableView.getItems().addAll(scheduleList.filterSchedulesByEventAndTeamName(currentTeam.getEventName(),currentTeam.getTeamName()).getActivityList());
    }

    public void showComment(CommentList comments){
        comment.getItems().setAll(comments.findComment(currentTeam.getTeamName()).getCommentList());
    }
}