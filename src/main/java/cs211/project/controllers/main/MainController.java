package cs211.project.controllers.main;

import cs211.project.controllers.event.EventItemController;
import cs211.project.models.account.Account;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.AccountList;
import cs211.project.models.collections.EventList;
import cs211.project.models.event.Event;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private Datasource<EventList> datasource;
    private EventList events;
    @FXML
    private Circle profilePic;
    @FXML private Hyperlink nameLink;
    private Account currentAccount;
    @FXML
    protected void onProfileButtonClick() throws IOException{
        FXRouter.goTo("profile");
    }

    @FXML
    private void onThisEventAsStaffButtonClick() throws IOException{
        FXRouter.goTo("myteam");
    }

    @FXML
    private void onCreateEventButtonClick() throws IOException{
        FXRouter.goTo("create");
    }

    @FXML
    private void onOngoingEventsButtonClick() throws IOException{
        FXRouter.goTo("eventAttended");
    }

    @FXML
    private void onEventInfoButtonClick() throws IOException{
        FXRouter.goTo("info");
    }
    @FXML
    private void goToEditEvent() throws IOException{
        FXRouter.goTo("creatorEventList",currentAccount);

    }

    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;

    private List<EventList> getData(){
        List<EventList> events = new ArrayList<>();
        EventList event;

//        for(int i = 0; i < 20; i++){
//            event = new Event();
//            event.setName("CR7 Meeting");
//            event.setImgSrc(getClass().getResource("/images/ronaldoi.png").toString());
//            events.add(event);
//        }
        return events;
    }
    @FXML
    public void initialize() {
//        currentAccount = (Account) FXRouter.getData();
        // read file outside project src
        File file = new File("data/images", LoggedInAccount.getInstance().getAccount().getImage());
        String path = "file:///" + file.getAbsolutePath();
        Image image = new Image(path);
        profilePic.setFill(new ImagePattern(image));
        nameLink.setText(LoggedInAccount.getInstance().getAccount().getUsername());
        datasource = new EventListDatasource();
        events = datasource.readData();
        int column = 0;
        int row = 0;

        try {
            for (Event event: events.getEvents()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-views.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                EventItemController eventItemController = fxmlLoader.getController();
                eventItemController.setData(event);

                anchorPane.setOnMouseClicked(Event -> {
                    try {
                        FXRouter.goTo("des", event);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                grid.add(anchorPane,column,row++);
                GridPane.setMargin(anchorPane,new Insets(10));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

