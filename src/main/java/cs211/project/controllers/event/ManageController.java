package cs211.project.controllers.event;

import cs211.project.controllers.login.UserListItemController;
import cs211.project.models.event.Schedule;
import cs211.project.models.collections.ScheduleList;
import cs211.project.models.event.Team;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.ParticipantList;
import cs211.project.models.collections.TeamList;
import cs211.project.models.event.Event;
import cs211.project.models.event.Participant;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;

public class ManageController {
    @FXML Button nameLabel;
    private Datasource<ParticipantList> datasource;
    private Datasource<TeamList> teamListDatasource;
    private Datasource<EventList> eventListDatasource;
    private EventList eventList;
    private TeamList teamList;
    private ParticipantList accounts;
    private String namePicture;
    private String startDateString;
    private String endDateString;
    private String temp = "join";
    @FXML
    private GridPane gridPane;
//    @FXML ComboBox;
//    private String selectTeam = "join";
    @FXML Rectangle picture;
    @FXML Label textTeam = null;
    @FXML TextField maximum;
    @FXML TextArea description;
    @FXML DatePicker startDate;
    @FXML DatePicker endDate;
    @FXML TextField eventName;

    @FXML Label play;
    @FXML
    public void sometime(){
        play.setTextFill(Color.BLACK);
        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, null, null);
        Background background = new Background(backgroundFill);
        play.setBackground(background);
    }
    @FXML
    public void something(){
        play.setTextFill(Color.web("#ffffff00"));
        BackgroundFill backgroundFill = new BackgroundFill(Color.web("#FFFFFF00"), null, null);
        Background background = new Background(backgroundFill);
        play.setBackground(background);
    }


    @FXML
    public void ClickToGoManu()throws IOException {
//        event.setEditEvent(eventName.getText(),description.getText(),maximum.getText(),namePicture,startDateString,endDateString);
        eventList.getEvents().stream()
                .filter(i -> i.getName().equals(event.getName()))
                .forEach(i -> i.setEditEvent(eventName.getText(),description.getText(),maximum.getText(),namePicture,startDateString,endDateString));
        scheduleList.getActivityList().stream()
                .filter(i -> i.getEventName().equals(event.getName()))
                .forEach(i -> i.setEventName(eventName.getText()));
        accounts.getParticipants().stream()
                .filter(i -> i.getEvent().equals(event.getName()))
                .forEach(i -> i.setEvent(eventName.getText()));
        datasource.writeData(accounts);
        datasourceSchedule.writeData(scheduleList);
        eventListDatasource.writeData(eventList);
        FXRouter.goTo("main");
    }

    private ScheduleList scheduleList;
    private Datasource<ScheduleList> datasourceSchedule;
    private Event event;
    @FXML private ComboBox selectTeam;
//    @FXML private ChoiceBox selectTeam;
    @FXML private TableView<Schedule> scheduleView;



    @FXML
    private void initialize(){
        event = (Event) FXRouter.getData();
        nameLabel.setText("Participant");



//        datasource = new AccountListDatasource();
//        accounts = datasource.readData();
        teamListDatasource = new TeamListDatasource("data","TeamList.csv");
        datasource = new ParticipantListDatasource();
        eventListDatasource = new EventListDatasource();
        eventList = eventListDatasource.readData();
        teamList = teamListDatasource.readData();
        accounts = datasource.readData();

        eventName.setText(event.getName());
        namePicture = event.getImgEvent();

        int[] date = event.splitDate(event.getDateStart());
        LocalDate defaultDate = LocalDate.of(date[2], date[1], date[0]);
        startDateString = date[2]+"-"+date[1]+"-"+date[0];
        startDate.setValue(defaultDate);
        date = event.splitDate(event.getDateEnd());
        defaultDate = LocalDate.of(date[2], date[1], date[0]);
        endDateString = date[2]+"-"+date[1]+"-"+date[0];
        endDate.setValue(defaultDate);

        File file = new File("data/images", event.getImgEvent());
        String path = "file:///" + file.getAbsolutePath();
        Image image = new Image(path);
        picture.setFill(new ImagePattern(image));

        description.setText(event.getDesc());

        maximum.setText(event.getMaximum());
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
                    GridPane.setMargin(anchorPane, new Insets(3));
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
                nameLabel.setDisable(false);
            }
        });
    }


    @FXML
    public void ClickToGoEditSchedule()throws IOException {
        event.tamp = temp;
        FXRouter.goTo("editSchedule",event);

    }
    @FXML void clickParticipant(){
        selectTeam.setValue("Select team");
        temp = "join";
        textTeam.setText("Select team");
        nameLabel.setDisable(true);
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
                    GridPane.setMargin(anchorPane, new Insets(3));
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
    @FXML
    public void handleUploadButton(MouseEvent choose) {
//
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) choose.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("data/images");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                //เปลี่ยนชื่อรูปภาพ
                String filename = event.getName() + "_pic" + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                namePicture = filename;
                Image image = new Image(target.toUri().toString());
                picture.setFill(new ImagePattern(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            namePicture = event.getImgEvent();

        }
    }


}