package cs211.project.controllers.main;

import cs211.project.controllers.event.EventItemController;
import cs211.project.models.account.Account;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.collections.EventList;
import cs211.project.models.event.Event;
import cs211.project.services.Datasource;
import cs211.project.services.DateSortcomparator;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private Datasource<EventList> datasource;
    private EventList events;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ComboBox<String> combox;
    @FXML
    private Circle profilePic;
    @FXML
    private Hyperlink nameLink;
    @FXML ImageView logo;
    private Account currentAccount;
    @FXML
    protected void onProfileButtonClick() throws IOException {
        FXRouter.goTo("profile");
    }
    @FXML
    private void onThisEventAsStaffButtonClick() throws IOException {
        FXRouter.goTo("myteam", events);
    }
    @FXML
    private void onCreateEventButtonClick() throws IOException {
        FXRouter.goTo("create");
    }
    @FXML
    private void onOngoingEventsButtonClick() throws IOException {
        FXRouter.goTo("eventAttended");
    }
    @FXML
    private void onEventInfoButtonClick() throws IOException {
        FXRouter.goTo("info");
    }
    @FXML
    private void goToEditEvent() throws IOException {
        FXRouter.goTo("creatorEventList", currentAccount);
    }
    @FXML
    private TextField srcField;
    @FXML
    private GridPane grid;

    private void sort(ActionEvent event) {
        if (combox.getValue().equals("Date")) {
            events.sort(new DateSortcomparator());
        }
        showGrid(events);
    }
    private List<EventList> getData() {
        List<EventList> events = new ArrayList<>();
        EventList event;
        return events;
    }

    private EventList filter(String src){
        EventList filtered = new EventList();
        for (Event event:events.getEvents()){
            if (event.getName().toLowerCase().contains(src.toLowerCase())){
                filtered.addEvent(event);
            }
        }
        return filtered;
    }
    @FXML
    public void initialize() {
        datasource = new EventListDatasource();
        events = datasource.readData();
        showGrid(events);
        srcField.textProperty().addListener((observable, oldValue, newValue)->{
            if (newValue != null && !newValue.isEmpty()) {
                showGrid(filter(newValue));
            }
            else {
                showGrid(events);
            }
        });
        String[] types = {"Date"};
        combox.getItems().addAll(types);
        combox.setOnAction(this::sort);
        File file = new File("src/data/images", LoggedInAccount.getInstance().getAccount().getImage());
        String path = "file:///" + file.getAbsolutePath();
        Image image = new Image(path);
        profilePic.setFill(new ImagePattern(image));
        nameLink.setText(LoggedInAccount.getInstance().getAccount().getUsername());
        String personImagePath = "file:data/images/tofu.png";
        Image personImage = new Image(personImagePath);
        logo.setImage(personImage);
    }

    private void showGrid(EventList events) {
        grid.getChildren().clear();
        int column = 0;
        int row = 0;

        LocalDateTime currentDateTime = LocalDateTime.now();

        try {
            for (Event event : events.getEvents()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                LocalDateTime eventStartDateTime = LocalDateTime.parse(event.getDateStart() + " " + event.getStartTime(), formatter);

                if (currentDateTime.isBefore(eventStartDateTime) || currentDateTime.isEqual(eventStartDateTime)) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-views.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    EventItemController eventItemController = fxmlLoader.getController();
                    eventItemController.setData(event);

                    anchorPane.setOnMouseClicked(eventClick -> {
                        if (eventClick.getClickCount() == 2) {
                        try {
                            FXRouter.goTo("des", event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    });

                    grid.add(anchorPane, column, row++);
                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            }
            scrollPane.setPannable(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

