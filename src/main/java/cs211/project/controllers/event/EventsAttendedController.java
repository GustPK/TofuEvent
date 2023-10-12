package cs211.project.controllers.event;

import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.EventList;
import cs211.project.models.collections.ParticipantList;
import cs211.project.models.event.Participant;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import cs211.project.services.ParticipantListDatasource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EventsAttendedController {
    private EventList eventsLists;
    private ParticipantList participantList;

    private Datasource<EventList> eventListDataSource;
    private Datasource<ParticipantList> participantListDatasource;
    @FXML
    private GridPane grid;


    @FXML
    public void initialize() throws IOException {
        participantListDatasource = new ParticipantListDatasource();
        eventListDataSource = new EventListDatasource();
        eventsLists = eventListDataSource.readData();
        participantList = participantListDatasource.readData();
        int column = 1;
        int row = 0;

        try {
            for(Participant participant : participantList.getParticipants()){
                if (LoggedInAccount.getInstance().getAccount().getUsername().equals(participant.getUsername())) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-views.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    EventItemController eventItemController = fxmlLoader.getController();
                    eventsLists.getEvents().stream()
                            .filter(i -> i.getName().equals(participant.getEvent()))
                            .forEach(schedule -> {
                                eventItemController.setData(schedule);
                            });

//                    anchorPane.setOnMouseClicked(events -> {
//                        try {
//                            FXRouter.goTo("manage", event);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    });

                    grid.add(anchorPane, column, row++);
                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
