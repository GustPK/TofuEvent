package cs211.project.controllers.creator;

import cs211.project.models.event.Schedule;
import cs211.project.models.collections.ScheduleList;
import cs211.project.models.event.Event;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import cs211.project.services.ScheduleFileDatasource;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class CreateParticipantsController {
    @FXML
    private TextField nameField;
    @FXML
    private TableView<Schedule> scheduleView;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Spinner<Integer> hourSpinner;
    @FXML
    private Spinner<Integer> minuteSpinner;
    private Event event;
    private ScheduleList scheduleList;
    private Datasource<ScheduleList> scheduleListDatasource;

    @FXML
    void initialize() {
        scheduleListDatasource = new ScheduleFileDatasource();
        scheduleList = scheduleListDatasource.readData();
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

        Event event = (Event) FXRouter.getData();
        event.page = "create";
    }


    private SpinnerValueFactory<Integer> createSpinnerValueFactory(int min, int max, int initialValue) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue);
        valueFactory.setConverter(new StringConverter<Integer>() {
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
        LocalDate date = datePicker.getValue();
        int hour = hourSpinner.getValue();
        int minute = minuteSpinner.getValue();
        if (name.isEmpty() || date == null || hour < 0 || minute < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Incomplete Information");
            alert.setContentText("Please fill out the information completely.");
            alert.showAndWait();
        } else {
            String time = String.format("%02d:%02d", hour, minute);

            scheduleList.addActivity(new Schedule(event.getName(), "join", name, time, date.toString()));
            showList(scheduleList);

            nameField.clear();
            hourSpinner.getValueFactory().setValue(0);
            minuteSpinner.getValueFactory().setValue(0);
        }
    }



    @FXML
    public void clickDelete() {
        ObservableList<Schedule> selectedRows = scheduleView.getSelectionModel().getSelectedItems();

        if (!selectedRows.isEmpty()) {
            scheduleList.getActivityList().removeAll(selectedRows);
            scheduleView.getItems().clear();
            showList(scheduleList);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("You must select items before deleting them.");
            alert.showAndWait();
        }
    }

    private void showList(ScheduleList scheduleList) {
        event = (Event) FXRouter.getData();
        List<Schedule> filter = scheduleList.filterSchedulesByEventAndTeamName(event.getName(),"join").getActivityList();

        Comparator<Schedule> customComparator = (schedule1, schedule2) -> {
            int dateComparison = schedule1.getDate().compareTo(schedule2.getDate());
            if (dateComparison == 0) {
                return schedule1.getTime().compareTo(schedule2.getTime());
            } else {
                return dateComparison;
            }
        };
        filter.sort(customComparator);
        scheduleView.getItems().setAll(filter);
    }

    @FXML
    private void clickNext() throws IOException {
        event = (Event) FXRouter.getData();
        List<Schedule> dataFromTableView = new ArrayList<>(scheduleView.getItems());

        if (dataFromTableView.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter at least 1 activity.");
            alert.showAndWait();
        } else {

            scheduleList.getActivityList().removeAll(scheduleList.filterSchedulesByEventAndTeamName(event.getName() , "join").getActivityList());
            scheduleList.getActivityList().addAll(dataFromTableView);

            scheduleListDatasource.writeData(scheduleList);
            FXRouter.goTo("createTeam", event);
        }
    }

}