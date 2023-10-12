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
import java.util.stream.Collectors;

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

        // ตรวจสอบว่าข้อมูลถูกครบถ้วน
        if (name.isEmpty() || team.isEmpty() || date == null) {
            // ข้อมูลไม่ถูกครบถ้วน
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Incomplete Information");
            alert.setContentText("Please fill out the information completely.");
            alert.showAndWait();
            return;
        }

        String time = String.format("%02d:%02d", hour, minute);
        scheduleList.addActivity(new Schedule(event.getName(), team, name, time, date.toString()));
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
        event = (Event) FXRouter.getData();
        List<Schedule> sortedList = new ArrayList<>(scheduleList.getActivityList());

        // แสดงเฉพาะชื่อ event ที่ซ้ำกัน
        List<Schedule> filteredList = sortedList.stream()
                .filter(schedule -> schedule.getEventName().equals(event.getName()))
                .collect(Collectors.toList());

        Comparator<Schedule> customComparator = (schedule1, schedule2) -> {
            int dateComparison = schedule1.getDate().compareTo(schedule2.getDate());
            if (dateComparison == 0) {
                return schedule1.getTime().compareTo(schedule2.getTime());
            } else {
                return dateComparison;
            }
        };

        filteredList.sort(customComparator);
        scheduleView.getItems().setAll(filteredList);
    }

    @FXML
    private void clickAddMore() throws IOException {
        // Read data from the data sources
        scheduleList = datasource.readData();
        teamList = datasource2.readData();

        // Get data from the scheduleView (table)
        List<Schedule> dataFromTableView = new ArrayList<>(scheduleView.getItems());

        // Get the event from the router (assuming that's how you're passing it)
        Event event = (Event) FXRouter.getData();

        String team = teamField.getText();
        String joinFieldText = joinField.getText();

        // Check if any of the required fields are empty
        if (team.isEmpty() || joinFieldText.isEmpty() || dataFromTableView.isEmpty()) {
            showAlert("Missing Information", "Please fill in all the required fields and add data to the table.");
        } else {
            // Check if the team name is duplicated within the same event
            if (!isTeamNameDuplicate(event.getName(), team)) {
                // Team name is unique within the event, proceed with team creation
                teamList.addTeam(new Team(event.getName(), team, joinFieldText, "0"));
                scheduleList.getActivityList().addAll(dataFromTableView);

                // Write data back to the data sources
                datasource.writeData(scheduleList);
                datasource2.writeData(teamList);

                // Navigate to a specific screen (e.g., "createTeam" in this case)
                FXRouter.goTo("createTeam");
            } else {
                showAlert("Team Name Duplicate", "Team name is already taken within this event.");
            }
        }
    }

    @FXML
    private void clickDone() throws IOException {
        event = (Event) FXRouter.getData();

        // Read data from the data sources
        scheduleList = datasource.readData();
        teamList = datasource2.readData();

        // Get data from the scheduleView (table)
        List<Schedule> dataFromTableView = new ArrayList<>(scheduleView.getItems());

        String team = teamField.getText();
        String joinFieldText = joinField.getText();

        // Check if any of the required fields are empty
        if (team.isEmpty() || joinFieldText.isEmpty() || dataFromTableView.isEmpty()) {
            showAlert("Missing Information", "Please fill in all the required fields and add data to the table.");
        } else {
            // Check if the team name is duplicated within the same event
            if (!isTeamNameDuplicate(event.getName(), team)) {
                // Team name is unique within the event, proceed with team creation
                teamList.addTeam(new Team(event.getName(), team, joinFieldText, "0"));
                scheduleList.getActivityList().addAll(dataFromTableView);

                // Write data back to the data sources
                datasource.writeData(scheduleList);
                datasource2.writeData(teamList);

                // Navigate to the "main" screen (or wherever you want to go)
                FXRouter.goTo("main");
            } else {
                showAlert("Team Name Duplicate", "Team name is already taken within this event.");
            }
        }
    }

    private boolean isTeamNameDuplicate(String eventName, String teamName) {
        // Iterate through the teams for the current event and check for duplicates
        for (Team team : teamList.getTeams()) {
            if (team.getEventName().equals(eventName) && team.getTeamName().equals(teamName)) {
                return true; // Team name is duplicated within the same event
            }
        }
        return false; // Team name is unique within the event
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
