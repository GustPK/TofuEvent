package cs211.project.controllers.event;

import cs211.project.controllers.login.UserListItemController;
import cs211.project.models.Schedule;
import cs211.project.models.ScheduleList;
import cs211.project.models.Team;
import cs211.project.models.collections.ParticipantList;
import cs211.project.models.collections.TeamList;
import cs211.project.models.event.Event;
import cs211.project.models.event.Participant;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManageController {
    @FXML Label nameLabel;
    private Datasource<ParticipantList> datasource;
    private Datasource<TeamList> teamListDatasource;
    private TeamList teamList;
    private ParticipantList accounts;
    private String temp = "join";
    @FXML
    private GridPane gridPane;
//    @FXML ComboBox;
//    private String selectTeam = "join";
    @FXML Label textTeam = null;

    @FXML
    public void clickBackToCreate() throws IOException {
        FXRouter.goTo("create");
    }
    @FXML
    public void ClickToGoMoreMange()throws IOException {
        FXRouter.goTo("manageInfo");
    }
    @FXML
    public void ClickToGoManu()throws IOException {
        FXRouter.goTo("main");
    }
    @FXML private ListView<Schedule> scheduleListView;
    private ScheduleList scheduleList;
    private Datasource<ScheduleList> datasourceSchedule;
    private Event event;
    @FXML private ComboBox selectTeam;
//    @FXML private ChoiceBox selectTeam;
    @FXML private TableView<Schedule> scheduleView;



    @FXML
    private void initialize(){
        event = (Event) FXRouter.getData();
        nameLabel.setText(event.getName());


//        datasource = new AccountListDatasource();
//        accounts = datasource.readData();
        teamListDatasource = new TeamListDatasource("data","TeamList.csv");
        datasource = new ParticipantListDatasource();
        teamList = teamListDatasource.readData();
        accounts = datasource.readData();
        int column = 0;
        int row = 0;
        try {
            for (Participant account : accounts.getParticipants()) {
                if (event.getName().equals(account.getEvent()) && account.getTeamName().equals(temp)) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/userlist-item-view.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    UserListItemController userListItemController = fxmlLoader.getController();
                    userListItemController.setData(account);

                    // Add an event handler to the AnchorPane to show the confirmation dialog when clicked
                    anchorPane.setOnMouseClicked(events -> {
                        // Create the confirmation dialog
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText(null);
                        alert.setContentText("You want ban this person?");

                        // Add "Ban" and "Cancel" buttons to the dialog
                        ButtonType buttonTypeBan = new ButtonType("Ban", ButtonBar.ButtonData.OK_DONE);
                        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(buttonTypeBan, buttonTypeCancel);

                        // Show the dialog and handle the user's choice
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == buttonTypeBan) {
                            account.setBanned();
                            datasource.writeData(accounts);
                            initialize();
                        }
                    });

                    gridPane.add(anchorPane, column, row++);
                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        datasourceSchedule = new ScheduleFileDatasource("data", "schedule.csv");
        scheduleList = datasourceSchedule.readData();
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
        activityColumn.setPrefWidth(100);

        scheduleView.getColumns().addAll(dateColumn, timeColumn, activityColumn,statusColumn);//
        scheduleList.getActivityList().stream()
                .filter(i -> i.getEventName().equals(event.getName()) && i.getTeamName().equals(temp))
                .forEach(k -> {
                    scheduleView.getItems().add(k);
                });
        scheduleView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Schedule selectedSchedule = scheduleView.getSelectionModel().getSelectedItem();
                if (selectedSchedule != null) {
                    // สร้าง Alert
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Set Status");
                    alert.setHeaderText("Choose the status for this schedule:");

                    // สร้างปุ่ม "Done" และ "Undone"
                    ButtonType buttonTypeDone = new ButtonType("Done");
                    ButtonType buttonTypeUndone = new ButtonType("Undone");

                    alert.getButtonTypes().setAll(buttonTypeDone, buttonTypeUndone);

                    // แสดง Alert และรอผู้ใช้เลือก
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent()) {
                        if (result.get() == buttonTypeDone) {
                            // ถ้าผู้ใช้เลือก "Done" ให้ใช้ setDone() บน selectedSchedule
                            selectedSchedule.setStatusDone();
                        } else if (result.get() == buttonTypeUndone) {
                            // ถ้าผู้ใช้เลือก "Undone" ให้ใช้ setUndone() บน selectedSchedule
                            selectedSchedule.setStatusUndone();
                        }

                        datasourceSchedule.writeData(scheduleList);
                        initialize();
                    }
                }
            }
        });




        for (Team team : teamList.getTeams()) {
            if (team.getEventName().equals(event.getName()))
                selectTeam.getItems().add(team.getTeamName());
        }
        selectTeam.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                temp = newValue.toString(); // กำหนดค่าที่เลือกใน ComboBox เข้าในตัวแปร temp
                textTeam.setText(null);
                sample();
            }
        });
    }


    @FXML
    public void ClickToGoEditSchedule()throws IOException {
        FXRouter.goTo("editSchedule",event);
    }
    @FXML void clickParticipant(){
        selectTeam.setValue("Select team");
        temp = "join";
        textTeam.setText("Select team");
        sample();
    }
    @FXML
    public void sample(){
        gridPane.getChildren().clear();
        scheduleView.getItems().clear();

        int column = 0;
        int row = 0;
        for (Participant account : accounts.getParticipants()) {
            if (event.getName().equals(account.getEvent()) && account.getTeamName().equals(temp)) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/userlist-item-view.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    UserListItemController userListItemController = fxmlLoader.getController();
                    userListItemController.setData(account);

                    anchorPane.setOnMouseClicked(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation");
                        alert.setHeaderText(null);
                        alert.setContentText("คุณต้องการจะban คนนี้หรือไม่?");

                        ButtonType buttonTypeBan = new ButtonType("Ban", ButtonBar.ButtonData.OK_DONE);
                        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(buttonTypeBan, buttonTypeCancel);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == buttonTypeBan) {
                            account.setBanned();
                            datasource.writeData(accounts);
                        }
                    });


                    gridPane.add(anchorPane, column, row++);
                    GridPane.setMargin(anchorPane, new Insets(10));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        scheduleList = datasourceSchedule.readData();
        scheduleList.getActivityList().stream()
                .filter(i -> i.getEventName().equals(event.getName()) && i.getTeamName().equals(temp))
                .forEach(k -> {
                    scheduleView.getItems().add(k);
                });

    }


}