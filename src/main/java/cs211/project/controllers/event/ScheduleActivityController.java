package cs211.project.controllers.event;

import cs211.project.models.event.Schedule;
import cs211.project.models.collections.ScheduleList;
import cs211.project.models.event.Event;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.ScheduleFileDatasource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class ScheduleActivityController {
    @FXML
    private Label nameLabel;
    @FXML
    private ImageView eventImageView;
    @FXML
    private TableView<Schedule> scheduleView;
    private Event event;
    private ScheduleList scheduleList;
    private Datasource<ScheduleList> datasource;

    @FXML
    private void BackButton() throws IOException {
        FXRouter.goTo("eventAttended");
    }

    @FXML
    private void initialize() {
        event = (Event) FXRouter.getData();
        nameLabel.setText(event.getName());
        String imagePath = "data/images/" + event.getImgEvent();
        File imageFile = new File(imagePath);
        Image eventImage = new Image(imageFile.toURI().toString());
        eventImageView.setImage(eventImage);

        datasource = new ScheduleFileDatasource("data", "schedule.csv");
        scheduleList = datasource.readData();
        scheduleView.getColumns().clear();
        scheduleView.getItems().clear();

        TableColumn<Schedule, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(100);

        TableColumn<Schedule, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeColumn.setPrefWidth(100);

        TableColumn<Schedule, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusColumn.setPrefWidth(100);

        TableColumn<Schedule, String> activityColumn = new TableColumn<>("Activity");
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        activityColumn.setPrefWidth(190);

        scheduleView.getColumns().addAll(dateColumn, timeColumn, activityColumn,statusColumn);//
        scheduleList.getActivityList().stream()
                .filter(i -> i.getEventName().equals(event.getName()) && i.getTeamName().equals("join"))
                .forEach(k -> {
                    scheduleView.getItems().add(k);
                });
    }
}
