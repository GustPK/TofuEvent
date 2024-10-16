package cs211.project.controllers.event;

import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.EventList;
import cs211.project.models.event.Event;
import cs211.project.models.collections.ParticipantList;
import cs211.project.models.event.Participant;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.ParticipantListDatasource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventsAttendedController {
    private EventList eventsLists;
    private ParticipantList participantList;
    private Datasource<EventList> eventListDataSource;
    private Datasource<ParticipantList> participantListDatasource;
    @FXML
    private GridPane grid;

    @FXML
    public void initialize() throws IOException {
        clearGrid();
        participantListDatasource = new ParticipantListDatasource();
        eventListDataSource = new EventListDatasource();
        eventsLists = eventListDataSource.readData();
        participantList = participantListDatasource.readData();
        int column = 1;
        int row = 0;

        Set<String> processedEvents = new HashSet<>();
        LocalDateTime currentDateTime = LocalDateTime.now();

        try {
            for (Participant participant : participantList.getParticipants()) {
                Event event;
                participant.checkEventName(participant.getEvent());
                event = eventsLists.check(participant.getEvent());

                if (event != null && participant.checkName(LoggedInAccount.getInstance().getAccount().getUsername())){
                    String eventName = participant.getEvent();

                    if (!processedEvents.contains(eventName)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                        LocalDateTime eventStartDateTime = LocalDateTime.parse(event.getDateStart() + " " + event.getStartTime(), formatter);

                        if (currentDateTime.isBefore(eventStartDateTime) || currentDateTime.isEqual(eventStartDateTime)) {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-view.fxml"));
                            AnchorPane anchorPane = fxmlLoader.load();

                            EventItemController eventItemController = fxmlLoader.getController();

                            eventItemController.setData(eventsLists.findByEventName(eventName));
                            anchorPane.setOnMouseClicked(event1 -> {
                                if ("banned".equals(participant.getBan())) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Banned Account");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Your account has been banned from this event");
                                    alert.showAndWait();

                                } else {
                                    try {
                                        goToSchedulePage(event);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            grid.add(anchorPane, column, row++);
                            GridPane.setMargin(anchorPane, new Insets(10));

                            processedEvents.add(eventName);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onHistoryHyperlinkClick() {
        clearGrid();
        participantListDatasource = new ParticipantListDatasource();
        eventListDataSource = new EventListDatasource();
        eventsLists = eventListDataSource.readData();
        participantList = participantListDatasource.readData();
        int column = 1;
        int row = 0;

        Set<String> processedEvents = new HashSet<>();
        LocalDateTime currentDateTime = LocalDateTime.now();

        try {
            for (Participant participant : participantList.getParticipants()) {
                Event event;
                participant.checkEventName(participant.getEvent());
                event = eventsLists.check(participant.getEvent());

                if (event != null && participant.checkName(LoggedInAccount.getInstance().getAccount().getUsername())) {
                    String eventName = participant.getEvent();

                    if (!processedEvents.contains(eventName)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                        LocalDateTime eventStartDateTime = LocalDateTime.parse(event.getDateStart() + " " + event.getStartTime(), formatter);

                        if (currentDateTime.isAfter(eventStartDateTime)) {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-view.fxml"));
                            AnchorPane anchorPane = fxmlLoader.load();

                            EventItemController eventItemController = fxmlLoader.getController();

                            eventItemController.setData(eventsLists.findByEventName(eventName));

                            anchorPane.setOnMouseClicked(event1 -> {
                                if ("banned".equals(participant.getBan())) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Banned Account");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Your account has been banned from this event");
                                    alert.showAndWait();
                                } else {
                                    try {
                                        goToSchedulePage(event);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            grid.add(anchorPane, column, row++);
                            GridPane.setMargin(anchorPane, new Insets(10));

                            processedEvents.add(eventName);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onUpcomingHyperlinkClick() {
        clearGrid();
        participantListDatasource = new ParticipantListDatasource();
        eventListDataSource = new EventListDatasource();
        eventsLists = eventListDataSource.readData();
        participantList = participantListDatasource.readData();
        int column = 1;
        int row = 0;

        Set<String> processedEvents = new HashSet<>();
        LocalDateTime currentDateTime = LocalDateTime.now();

        try {
            for (Participant participant : participantList.getParticipants()) {
                Event event;
                participant.checkEventName(participant.getEvent());
                event = eventsLists.check(participant.getEvent());

                if (event != null && participant.checkName(LoggedInAccount.getInstance().getAccount().getUsername())) {
                    String eventName = participant.getEvent();

                    if (!processedEvents.contains(eventName)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                        LocalDateTime eventStartDateTime = LocalDateTime.parse(event.getDateStart() + " " + event.getStartTime(), formatter);

                        if (currentDateTime.isBefore(eventStartDateTime) || currentDateTime.isEqual(eventStartDateTime)) {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-view.fxml"));
                            AnchorPane anchorPane = fxmlLoader.load();

                            EventItemController eventItemController = fxmlLoader.getController();

                            eventItemController.setData(eventsLists.findByEventName(eventName));

                            anchorPane.setOnMouseClicked(event1 -> {
                                if ("banned".equals(participant.getBan())) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Banned Account");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Your account has been banned from this event");
                                    alert.showAndWait();
                                } else {
                                    try {
                                        goToSchedulePage(event);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            grid.add(anchorPane, column, row++);
                            GridPane.setMargin(anchorPane, new Insets(10));

                            processedEvents.add(eventName);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickBackToMain() throws IOException {
        FXRouter.goTo("main");
    }
    private void clearGrid() {
        grid.getChildren().clear();
    }
    private void goToSchedulePage(Event event) throws IOException {
        FXRouter.goTo("scheduleActivity", event);
    }
}