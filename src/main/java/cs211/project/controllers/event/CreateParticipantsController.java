package cs211.project.controllers.event;

import cs211.project.models.Schedule;
import cs211.project.models.ScheduleList;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.AccountList;
import cs211.project.models.collections.EventList;
import cs211.project.models.event.Event;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.ScheduleFileDatasource;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    private ScheduleList scheduleList = new ScheduleList();
    private Datasource<ScheduleList> datasource;

    @FXML
    private void initialize() {
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

        Event event = (Event) FXRouter.getData();
        if (event != null) {
            String eventName = event.getName();
            filterSchedulesByEventAndTeamName(eventName);
        }

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

        // รับข้อมูลจาก nameField
        String name = nameField.getText();

        // รับวันที่จาก datePicker
        LocalDate date = datePicker.getValue();

        // รับค่าจาก hourSpinner และ minuteSpinner
        int hour = hourSpinner.getValue();
        int minute = minuteSpinner.getValue();

        // นำข้อมูลไปเพิ่มลงใน scheduleList โดยรวมเป็น "hh:mm"
        String time = String.format("%02d:%02d", hour, minute);
        scheduleList.addActivity(new Schedule(event.getName(), "join", name, time, date.toString()));

        scheduleView.getItems().clear();

        showList(scheduleList);

        // ล้างค่าใน nameField, hourSpinner และ minuteSpinner หลังจากเพิ่มข้อมูลเสร็จ
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
    private void clickNext() throws IOException {
        scheduleList = datasource.readData();
        List<Schedule> dataFromTableView = new ArrayList<>(scheduleView.getItems());
        scheduleList.getActivityList().addAll(dataFromTableView);

        // Write the data to your datasource if needed
        datasource.writeData(scheduleList);

        // Redirect to the "CreateTeam" view and pass the event stored in the field
        FXRouter.goTo("createTeam", event);
    }

    private void filterSchedulesByEventAndTeamName(String eventName) {
        scheduleList = datasource.readData();
        for (Schedule schedule : scheduleList.getActivityList() ){
            if (schedule.getEventName().equals(eventName) && schedule.getTeamName().equals("join")){
                scheduleView.getItems().add(schedule);
                System.out.println(schedule.getEventName());
            }
        }

    }

}