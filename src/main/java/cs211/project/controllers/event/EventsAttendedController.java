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
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.HashSet;
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

        try {
            for (Participant participant : participantList.getParticipants()) {
                Event event = eventsLists.getEvents().stream()
                        .filter(e -> e.getName().equals(participant.getEvent()))
                        .findFirst()
                        .orElse(null);
                if (event != null && LoggedInAccount.getInstance().getAccount().getUsername().equals(participant.getUsername()) && "UNDONE".equals(event.getStatus())) {
                    String eventName = participant.getEvent();

                    if (!processedEvents.contains(eventName)) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-views.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();

                        EventItemController eventItemController = fxmlLoader.getController();
                        eventsLists.getEvents().stream()
                                .filter(i -> i.getName().equals(eventName))
                                .forEach(schedule -> {
                                    eventItemController.setData(schedule);
                                });

                        grid.add(anchorPane, column, row++);
                        GridPane.setMargin(anchorPane, new Insets(10));
                        processedEvents.add(eventName);
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

        try {
            for (Participant participant : participantList.getParticipants()) {
                Event event = eventsLists.getEvents().stream()
                        .filter(e -> e.getName().equals(participant.getEvent()))
                        .findFirst()
                        .orElse(null);
                if (event != null && LoggedInAccount.getInstance().getAccount().getUsername().equals(participant.getUsername()) && "DONE".equals(event.getStatus())) {
                    String eventName = participant.getEvent();

                    if (!processedEvents.contains(eventName)) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-views.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();

                        EventItemController eventItemController = fxmlLoader.getController();
                        eventsLists.getEvents().stream()
                                .filter(i -> i.getName().equals(eventName))
                                .forEach(schedule -> {
                                    eventItemController.setData(schedule);
                                });

                        grid.add(anchorPane, column, row++);
                        GridPane.setMargin(anchorPane, new Insets(10));

                        processedEvents.add(eventName);
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

        try {
            for (Participant participant : participantList.getParticipants()) {
                Event event = eventsLists.getEvents().stream()
                        .filter(e -> e.getName().equals(participant.getEvent()))
                        .findFirst()
                        .orElse(null);
                if (event != null && LoggedInAccount.getInstance().getAccount().getUsername().equals(participant.getUsername()) && "UNDONE".equals(event.getStatus())) {
                    String eventName = participant.getEvent();

                    if (!processedEvents.contains(eventName)) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-views.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();

                        EventItemController eventItemController = fxmlLoader.getController();
                        eventsLists.getEvents().stream()
                                .filter(i -> i.getName().equals(eventName))
                                .forEach(schedule -> {
                                    eventItemController.setData(schedule);
                                });

                        grid.add(anchorPane, column, row++);
                        GridPane.setMargin(anchorPane, new Insets(10));

                        processedEvents.add(eventName);
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
}
