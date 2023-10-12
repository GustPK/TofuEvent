package cs211.project.controllers.event;

import cs211.project.controllers.login.UserListItemController;
import cs211.project.models.account.Account;
import cs211.project.models.account.LoggedInAccount;
import cs211.project.models.event.Event;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EditEventController {


    private EventList eventsLists;

    private Datasource<EventList> EventListDataSource;
    @FXML
    private GridPane grid;

    @FXML
    public void initialize() throws IOException {
        EventListDataSource = new EventListDatasource();
        eventsLists = EventListDataSource.readData();
        int column = 1;
        int row = 0;

        try {
            for (Event event : eventsLists.getEvents()) {
                if (LoggedInAccount.getInstance().getAccount().getUsername().equals(event.getOrganizer())) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/cs211/project/views/event-item-views.fxml"));
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
    }
