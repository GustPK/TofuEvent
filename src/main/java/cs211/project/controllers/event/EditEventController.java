package cs211.project.controllers.event;

import cs211.project.models.event.Event;
import cs211.project.models.collections.EventList;
import cs211.project.services.Datasource;
import cs211.project.services.EventListDatasource;
import cs211.project.services.FXRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class EditEventController {

    @FXML private TableView<Event> eventsTable;

    private EventList eventsLists;

    private Datasource<EventList> EventListDataSource;

    @FXML
    public void initialize(){
        EventListDataSource = new EventListDatasource();
        eventsLists = EventListDataSource.readData();
        showTable(eventsLists);

        eventsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> oldValue, Event event, Event newValue) {
                if (newValue != null){
                    try{
                        FXRouter.goTo("manageInfo", newValue);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void showTable(EventList eventsList){

        TableColumn<Event, String > nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(200);

        TableColumn<Event, String > dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setMinWidth(200);

        eventsTable.getColumns().clear();
        eventsTable.getColumns().addAll(nameColumn,dateColumn);

        eventsTable.getItems().clear();

        for(Event event : eventsList.getEvents()){
            eventsTable.getItems().add(event);
//            System.out.println(event.getName()+" "+event.getDate());
        }
    }

}
