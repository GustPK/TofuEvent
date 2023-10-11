package cs211.project.controllers.event;

import cs211.project.models.Schedule;
import cs211.project.models.ScheduleList;
import cs211.project.models.Team;
import cs211.project.models.collections.TeamList;
import cs211.project.models.event.Event;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.ScheduleFileDatasource;
import cs211.project.services.TeamListDatasource;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CreateTeamController {
    @FXML
    private TextField teamField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField joinField;
    @FXML
    private TableView<Schedule> scheduleView;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Spinner<Integer> hourSpinner;
    @FXML
    private Spinner<Integer> minuteSpinner;
    private Event event;
    private ScheduleList scheduleList = new ScheduleList();
    private Datasource<ScheduleList> datasource;
    private TeamList teamList = new TeamList();
    private Datasource<TeamList> datasource2;

    @FXML
    private void initialize() {
        datasource = new ScheduleFileDatasource("data", "schedule.csv");
        datasource2 = new TeamListDatasource("data", "TeamList.csv");

        scheduleView.getColumns().clear();
        scheduleView.getItems().clear();

        TableColumn<Schedule, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(125);

        TableColumn<Schedule, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeColumn.setPrefWidth(125);

        TableColumn<Schedule, String> activityColumn = new TableColumn<>("Activity");
        activityColumn.setCellValueFactory(new PropertyValueFactory<>("activity"));
        activityColumn.setPrefWidth(125);

        scheduleView.getColumns().addAll(dateColumn, timeColumn, activityColumn);

        SpinnerValueFactory<Integer> hourStartValueFactory = createSpinnerValueFactory(0, 23, 0);
        SpinnerValueFactory<Integer> minuteStartValueFactory = createSpinnerValueFactory(0, 59, 0);
        hourSpinner.setValueFactory(hourStartValueFactory);
        minuteSpinner.setValueFactory(minuteStartValueFactory);
    }

    private SpinnerValueFactory<Integer> createSpinnerValueFactory(int min, int max, int initialValue) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue);
        valueFactory.setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                return 0;
            }
        });
        return valueFactory;
    }

    @FXML
    public void clickAdd() {
        event = (Event) FXRouter.getData();

        String name = nameField.getText();
        String team = teamField.getText();
        LocalDate date = datePicker.getValue();

        int hour = hourSpinner.getValue();
        int minute = minuteSpinner.getValue();

        String time = String.format("%02d:%02d", hour, minute);
        scheduleList.addActivity(event.getName(), team, name, time, date.toString());

        scheduleView.getItems().clear();

        showList(scheduleList);

        nameField.clear();
        hourSpinner.getValueFactory().setValue(0);
        minuteSpinner.getValueFactory().setValue(0);
    }

    @FXML
    public void clickDelete() {
        ObservableList<Schedule> selectedRows = scheduleView.getSelectionModel().getSelectedItems();

        scheduleList.getActivityList().removeAll(selectedRows);

        scheduleView.getItems().clear();
        showList(scheduleList);
    }

    private void showList(ScheduleList scheduleList) {
        List<Schedule> sortedList = new ArrayList<>(scheduleList.getActivityList());

        Comparator<Schedule> customComparator = (schedule1, schedule2) -> {
            int dateComparison = schedule1.getDate().compareTo(schedule2.getDate());
            if (dateComparison == 0) {
                return schedule1.getTime().compareTo(schedule2.getTime());
            } else {
                return dateComparison;
            }
        };

        sortedList.sort(customComparator);

        scheduleView.getItems().setAll(sortedList);
    }
    @FXML
    private void clickAddMore() throws IOException {
        scheduleList = datasource.readData();
        teamList = datasource2.readData();
        List<Schedule> dataFromTableView = new ArrayList<>(scheduleView.getItems());

        Event event = (Event) FXRouter.getData();
        String team = teamField.getText();
        String joinFieldText = joinField.getText();

        teamList.addTeam(new Team(event.getName(), team, joinFieldText));
        scheduleList.getActivityList().addAll(dataFromTableView);

        datasource.writeData(scheduleList);
        datasource2.writeData(teamList);

        FXRouter.goTo("createTeam");
    }
    @FXML
    private void clickDone() throws IOException {
        scheduleList = datasource.readData();
        teamList = datasource2.readData();
        List<Schedule> dataFromTableView = new ArrayList<>(scheduleView.getItems());

        Event event = (Event) FXRouter.getData();
        String team = teamField.getText();
        String joinFieldText = joinField.getText();

        teamList.addTeam(new Team(event.getName(), team, joinFieldText));
        scheduleList.getActivityList().addAll(dataFromTableView);

        datasource.writeData(scheduleList);
        datasource2.writeData(teamList);

        FXRouter.goTo("main");
    }
}
