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
import javafx.util.StringConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    @FXML
    private Spinner<Integer> hourSpinnerStart;
    @FXML
    private Spinner<Integer> minuteSpinnerStart;
    @FXML
    private Spinner<Integer> hourSpinnerEnd;
    @FXML
    private Spinner<Integer> minuteSpinnerEnd;

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

    private ScheduleList scheduleList;
    private Datasource<ScheduleList> datasourceSchedule;
    private Event event;
    @FXML private ComboBox selectTeam;
//    @FXML private ChoiceBox selectTeam;
    @FXML private TableView<Schedule> scheduleView;

    private SpinnerValueFactory<Integer> createSpinnerValueFactory(int min, int max, int initialValue) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initialValue);
        valueFactory.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return String.format("%02d", value);
            }

            @Override
            public Integer fromString(String string) {
                if (string == null || string.isEmpty()) {
                    return 0;
                }
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    return 0; // Return a default value or handle the error as needed
                }
            }
        });
        return valueFactory;
    }

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
        startDateString = String.format("%02d-%02d-%04d", date[0], date[1], date[2]);
        startDate.setValue(defaultDate);
        date = event.splitDate(event.getDateEnd());
        defaultDate = LocalDate.of(date[2], date[1], date[0]);
        endDateString = String.format("%02d-%02d-%04d", date[0], date[1], date[2]);
        endDate.setValue(defaultDate);


        File file = new File("data/images", event.getImgEvent());
        String path = "file:///" + file.getAbsolutePath();
        Image image = new Image(path);
        picture.setFill(new ImagePattern(image));

        description.setText(event.getDesc());

        maximum.setText(event.getMaximum());
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

                        if ("banned".equals(account.getBan())) {
                            alert.setContentText("You want to unban this person?");
                            account.setBan("unbanned");
                        } else {
                            alert.setContentText("You want to ban this person?");
                            account.setBan("banned");
                        }

                        ButtonType buttonTypeBanUnban = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(buttonTypeBanUnban, buttonTypeCancel);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == buttonTypeBanUnban) {
                            datasource.writeData(accounts);
                            refreshGridPane();
                        }
                    });

                    gridPane.add(anchorPane, column, row++);
                    GridPane.setMargin(anchorPane, new Insets(10));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Set Status");
                    alert.setHeaderText("Choose the status for this schedule:");

                    ButtonType buttonTypeDone = new ButtonType("Done");
                    ButtonType buttonTypeUndone = new ButtonType("Undone");

                    alert.getButtonTypes().setAll(buttonTypeDone, buttonTypeUndone);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent()) {
                        if (result.get() == buttonTypeDone) {
                            selectedSchedule.setStatusDone();
                        } else if (result.get() == buttonTypeUndone) {
                            selectedSchedule.setStatusUndone();
                        }

                        datasourceSchedule.writeData(scheduleList);
                        initialize();
                    }
                }
            }
        });
        int[] time = event.splitTime(event.getStartTime());
        SpinnerValueFactory<Integer> hourStartValueFactory = createSpinnerValueFactory(0, 23, time[0]);
        SpinnerValueFactory<Integer> minuteStartValueFactory = createSpinnerValueFactory(0, 59, time[1]);
        time = event.splitTime(event.getEndTime());
        SpinnerValueFactory<Integer> hourEndValueFactory = createSpinnerValueFactory(0, 23, time[0]);
        SpinnerValueFactory<Integer> minuteEndValueFactory = createSpinnerValueFactory(0, 59, time[1]);

        hourSpinnerStart.setValueFactory(hourStartValueFactory);
        minuteSpinnerStart.setValueFactory(minuteStartValueFactory);
        hourSpinnerEnd.setValueFactory(hourEndValueFactory);
        minuteSpinnerEnd.setValueFactory(minuteEndValueFactory);


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
    public void ClickToGoManu() throws IOException {
        int startHour = hourSpinnerStart.getValue();
        int startMinute = minuteSpinnerStart.getValue();
        int endHour = hourSpinnerEnd.getValue();
        int endMinute = minuteSpinnerEnd.getValue();
        String startTimeString = String.format("%02d:%02d", startHour, startMinute);
        String endTimeString = String.format("%02d:%02d", endHour, endMinute);

        // สลับสองเวลาให้ไปอยู่ด้านหลัง
        eventList.getEvents().stream()
                .filter(i -> i.getName().equals(event.getName()))
                .forEach(i -> {
                    i.setEditEvent(eventName.getText(), description.getText(), maximum.getText(), namePicture, endTimeString, startTimeString, endDateString, startDateString);
                });

        // อัพเดทข้อมูลใน datasource
        datasource.writeData(accounts);
        datasourceSchedule.writeData(scheduleList);
        eventListDatasource.writeData(eventList);

        FXRouter.goTo("main");
    }
    @FXML
    public void ClickAddMoreTeam()throws IOException {
        event.tamp = temp;
        FXRouter.goTo("createTeam",event);

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

                        if ("banned".equals(account.getBan())) {
                            alert.setContentText("You want to unban this person?");
                            account.setBan("unbanned");
                        } else {
                            alert.setContentText("You want to ban this person?");
                            account.setBan("banned");
                        }

                        ButtonType buttonTypeBanUnban = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(buttonTypeBanUnban, buttonTypeCancel);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == buttonTypeBanUnban) {
                            datasource.writeData(accounts);
                            refreshGridPane();
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
    public void refreshGridPane() {
        gridPane.getChildren().clear();

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

                        if ("banned".equals(account.getBan())) {
                            alert.setContentText("You want to unban this person?");
                            account.setBan("unbanned");
                        } else {
                            alert.setContentText("You want to ban this person?");
                            account.setBan("banned");
                        }

                        ButtonType buttonTypeBanUnban = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(buttonTypeBanUnban, buttonTypeCancel);

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == buttonTypeBanUnban) {
                            datasource.writeData(accounts);
                            refreshGridPane();
                        }
                    });

                    gridPane.add(anchorPane, column, row++);
                    GridPane.setMargin(anchorPane, new Insets(10));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private boolean isEventNameDuplicate(String name) {
        for (Event event : eventList.getEvents()) {
            if (event.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
