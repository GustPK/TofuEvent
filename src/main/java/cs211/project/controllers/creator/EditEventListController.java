package cs211.project.controllers.creator;

import cs211.project.controllers.event.EventItemController;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.event.Event;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EditEventListController {


    @FXML
    private GridPane grid;

    @FXML
    public void initialize() throws IOException {
        Datasource<EventList> eventListDataSource = new EventListDatasource();
        EventList eventsLists = eventListDataSource.readData();
        int column = 1;
        int row = 0;

        try {
            for (Event event : eventsLists.getEvents()) {
                if (event.checkOrganizerName(LoggedInAccount.getInstance().getAccount().getUsername())) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-view.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    EventItemController eventItemController = fxmlLoader.getController();
                    eventItemController.setData(event);

                    anchorPane.setOnMouseClicked(events -> {
                        try {
                            FXRouter.goTo("manage", event);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    grid.add(anchorPane, column, row++);
                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            }
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    @FXML
    protected void onBackButtonClick() throws IOException {
        FXRouter.goTo("main");
    }
    }
