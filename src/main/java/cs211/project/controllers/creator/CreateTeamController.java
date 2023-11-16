package cs211.project.controllers.creator;

import cs211.project.models.event.Schedule;
import cs211.project.models.collections.ScheduleList;
import cs211.project.models.event.Team;
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
    @FXML
    private Button button;
    public static String page;
    private Event event;
    private ScheduleList scheduleList = new ScheduleList();
    private Datasource<ScheduleList> scheduleListDatasource;
    private TeamList teamList = new TeamList();
    private Datasource<TeamList> teamListDatasource;


    @FXML
    public void initialize() {
        scheduleListDatasource = new ScheduleFileDatasource("data", "schedule.csv");
        teamListDatasource = new TeamListDatasource("data", "TeamList.csv");

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


        if (page.equals("creat")) button.setText("Later");
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
        scheduleList = scheduleListDatasource.readData();
        teamList = teamListDatasource.readData();

        List<Schedule> dataFromTableView = new ArrayList<>(scheduleView.getItems());

        Event event = (Event) FXRouter.getData();

        String team = teamField.getText();
        String joinFieldText = joinField.getText();

        if (team.isEmpty() || joinFieldText.isEmpty() || dataFromTableView.isEmpty()) {
            showAlert("Missing Information", "Please fill in all the required fields and add data to the table.");
        } else {
            if (!isTeamNameDuplicate(event.getName(), team)) {
                teamList.addTeam(new Team(event.getName(), team, joinFieldText, "0"));
                scheduleList.getActivityList().addAll(dataFromTableView);

                scheduleListDatasource.writeData(scheduleList);
                teamListDatasource.writeData(teamList);

                FXRouter.goTo("createTeam");
            } else {
                showAlert("Team Name Duplicate", "Team name is already taken within this event.");
            }
        }
    }

    @FXML
    private void clickDone() throws IOException {
        event = (Event) FXRouter.getData();

        scheduleList = scheduleListDatasource.readData();
        teamList = teamListDatasource.readData();

        List<Schedule> dataFromTableView = new ArrayList<>(scheduleView.getItems());

        String team = teamField.getText();
        String joinFieldText = joinField.getText();

        if (team.isEmpty() || joinFieldText.isEmpty() || dataFromTableView.isEmpty()) {
            showAlert("Missing Information", "Please fill in all the required fields and add data to the table.");
        } else {
            if (!isTeamNameDuplicate(event.getName(), team)) {
                teamList.addTeam(new Team(event.getName(), team, joinFieldText, "0"));
                scheduleList.getActivityList().addAll(dataFromTableView);

                scheduleListDatasource.writeData(scheduleList);
                teamListDatasource.writeData(teamList);

                if(event.tamp == null) {
                    FXRouter.goTo("main");
                }else {
                    FXRouter.goTo("manage",event);
                }
            }else {
                showAlert("Team Name Duplicate", "Team name is already taken within this event.");
            }
        }
    }
    @FXML
    protected void onBackButtonClick() throws IOException {
        FXRouter.goTo(page.equals("manage") ? "manage" : "main");
    }

    private boolean isTeamNameDuplicate(String eventName, String teamName) {
        for (Team team : teamList.getTeams()) {
            if (team.getEventName().equals(eventName) && team.getTeamName().equals(teamName)) {
                return true;
            }
        }
        return false;
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
