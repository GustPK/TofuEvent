package cs211.project.controllers.event;

import cs211.project.controllers.login.UserListItemController;
import cs211.project.models.Schedule;
import cs211.project.models.ScheduleList;
import cs211.project.models.collections.ParticipantList;
import cs211.project.models.event.Event;
import cs211.project.models.event.Participant;
import cs211.project.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;

public class ManageController {
    @FXML Label nameLabel;
    private Datasource<ParticipantList> datasource;
    private ParticipantList accounts;
    @FXML
    private GridPane gridPane;


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


    @FXML
    private void initialize(){
        event = (Event) FXRouter.getData();
        nameLabel.setText(event.getName());


//        datasource = new AccountListDatasource();
//        accounts = datasource.readData();
        datasource = new ParticipantListDatasource();
        accounts = datasource.readData();
        int column = 0;
        int row = 0;
        try {
            for (Participant account : accounts.getParticipants()) {
                if (event.getName().equals(account.getEvent()) && account.getTeamName().equals("join")) {
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
                        alert.setContentText("คุณต้องการจะban คนนี้หรือไม่?");

                        // Add "Ban" and "Cancel" buttons to the dialog
                        ButtonType buttonTypeBan = new ButtonType("Ban", ButtonBar.ButtonData.OK_DONE);
                        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(buttonTypeBan, buttonTypeCancel);

                        // Show the dialog and handle the user's choice
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == buttonTypeBan) {
                            account.setBanned();
                            datasource.writeData(accounts);
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
        showList(scheduleList);
    }

    private void showList(ScheduleList scheduleList) {
        scheduleListView.getItems().addAll(scheduleList.getActivityList());
    }

    @FXML
    public void ClickToGoEditSchedule()throws IOException {
        FXRouter.goTo("createParticipants",event);
    }
}
