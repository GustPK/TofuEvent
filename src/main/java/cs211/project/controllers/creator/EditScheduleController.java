package cs211.project.controllers.creator;

import cs211.project.models.account.Account;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EditScheduleController {
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
    private ScheduleList scheduleList = new ScheduleList();
    private Datasource<ScheduleList> datasource;
    private Event getEvent ;
    String temp;


    @FXML
    private void initialize() {
        getEvent = (Event) FXRouter.getData();
        temp = getEvent.tamp;
        datasource = new ScheduleFileDatasource("data", "schedule.csv");

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


        filterSchedulesByEventAndTeamName(getEvent.getName(),temp);


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

        String name = nameField.getText();
        LocalDate date = datePicker.getValue();

        int hour = hourSpinner.getValue();
        int minute = minuteSpinner.getValue();


        String time = String.format("%02d:%02d", hour, minute);
        scheduleList.addActivity(new Schedule(getEvent.getName(), temp, name, time, date.toString()));
        showList(scheduleList);
        nameField.clear();
        hourSpinner.getValueFactory().setValue(0);
        minuteSpinner.getValueFactory().setValue(0);
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
            alert.setHeaderText("Please select before delete");
            alert.showAndWait();
        }
    }

    private void showList(ScheduleList scheduleList) {

        List<Schedule> sortedList = new ArrayList<>(scheduleList.getActivityList());
        List<Schedule> filteredList = sortedList.stream()
                .filter(schedule -> schedule.getEventName().equals(getEvent.getName()) && schedule.getTeamName().equals(temp))
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
    private void clickNext() throws IOException {
        getEvent = (Event) FXRouter.getData();
        scheduleList = datasource.readData();
        List<Schedule> dataFromTableView = new ArrayList<>(scheduleView.getItems());
        scheduleList.getActivityList().removeIf(schedule ->
                schedule.getEventName().equals(getEvent.getName()) && schedule.getTeamName().equals(temp)
        );
        scheduleList.getActivityList().addAll(dataFromTableView);
        datasource.writeData(scheduleList);
        FXRouter.goTo("manage");
    }
    @FXML
    protected void onBackButtonClick() throws IOException {
        FXRouter.goTo("manage");
    }

    private void filterSchedulesByEventAndTeamName(String eventName,String temp) {
        scheduleList = datasource.readData();
        for (Schedule schedule : scheduleList.getActivityList() ){
            if (schedule.getEventName().equals(eventName) && schedule.getTeamName().equals(temp)){
                scheduleView.getItems().add(schedule);
            }
        }
    }

}
