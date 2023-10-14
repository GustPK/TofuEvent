package cs211.project.controllers.team;

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
    private Team selectedTeam;
    private Datasource<CommentList> commentListDatasource;
    private CommentList commentList;

    @FXML
    public void clickBackToMyTeam() throws IOException {
        FXRouter.goTo("myteam");
    }

    @FXML
    public void onClickComment(){
        String c = commentField.getText();
        Comment comment = new Comment(currentTeam.getTeamName(), c, currentTeam.getEventName());
        this.comment.getItems().add(comment);
        commentList.addComment(currentTeam.getTeamName(), c, currentTeam.getEventName());
        commentListDatasource.writeData(commentList);
    }
    @FXML
    public void initialize(){
        currentTeam = (Team) FXRouter.getData();
        activityListDatasource = new ScheduleFileDatasource("data", "Schedule.csv");
        scheduleList = activityListDatasource.readData();
        commentListDatasource = new CommentListDatasource("data", "comment.csv");
        commentList = commentListDatasource.readData();
        showComment(commentList);

        showActivity();
    }

    public void showActivity() {
        activityTableView.getColumns().clear();
        activityTableView.getItems().clear();
        TableColumn<Schedule, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Schedule, String> activityColumn = new TableColumn<>("Activity");
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));

        activityTableView.getColumns().addAll(timeColumn, activityColumn);

        for (Schedule schedule : scheduleList.getActivityList()) {
            if (schedule.getTeamName().equals(currentTeam.getTeamName()) && schedule.getEventName().equals(currentTeam.getEventName())) {
                activityTableView.getItems().add(schedule);
            }
        }
    }

    public void showComment(CommentList comments){
        for(Comment c: comments.getCommentList()){
            if(c.getTeamName().equals(currentTeam.getTeamName())){
                comment.getItems().add(c);
            }
        }
    }
}
