package cs211.project.controllers.main;

import cs211.project.controllers.event.EventItemController;
import cs211.project.model.Event;
import cs211.project.model.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import cs211.project.services.EventListFileDatasource ;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private Datasource<EventList> datasource;
    private EventList events;
    @FXML
    private Circle profilePic;
    @FXML
    protected void onProfileButtonClick() throws IOException{
        FXRouter.goTo("profile");
    }

    @FXML
    private void onThisEventAsStaffButtonClick() throws IOException{
        FXRouter.goTo("team");
    }

    @FXML
    private void onCreateEventButtonClick() throws IOException{
        FXRouter.goTo("create");
    }

    @FXML
    private void onOngoingEventsButtonClick() throws IOException{
        FXRouter.goTo("ongoing");
    }

    @FXML
    private void onEventInfoButtonClick() throws IOException{
        FXRouter.goTo("info");
    }
    @FXML
    private void goToEditEvent() throws IOException{
        FXRouter.goTo("creatorEventList");
    }

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;

    private List<Event> getData(){
        List<Event> events = new ArrayList<>();
        Event event;

//        for(int i = 0; i < 20; i++){
//            event = new Event();
//            event.setName("CR7 Meeting");
//            event.setImgSrc(getClass().getResource("/images/ronaldo.png").toString());
//            events.add(event);
//        }
        return events;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datasource = new EventListFileDatasource("data","event.csv");
        events = datasource.readData();
        int column = 0;
        int row = 0;

        try {
            for (Event event: events.getEventList()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-views.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                EventItemController eventItemController = fxmlLoader.getController();
                eventItemController.setData(event);

                grid.add(anchorPane,column,row++);
                GridPane.setMargin(anchorPane,new Insets(10));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

