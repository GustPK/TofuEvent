package cs211.project.controllers.event;

import cs211.project.models.event.Eva;
import cs211.project.models.event.EvaList;
import cs211.project.services.Datasource;
import cs211.project.services.EvaDataSource;
import cs211.project.services.FXRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class EditEventController {

    @FXML private TableView<Eva> eventsTable;

    private EvaList eventsLists;

    private Datasource<EvaList> EventListDataSource;

    @FXML
    public void initialize(){
        EventListDataSource = new EvaDataSource();
        eventsLists = EventListDataSource.readData();
        showTable(eventsLists);

        eventsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Eva>() {
            @Override
            public void changed(ObservableValue<? extends Eva> oldValue, Eva event, Eva newValue) {
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

    private void showTable(EvaList eventsList){

        TableColumn<Eva, String > nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(200);

        TableColumn<Eva, String > dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setMinWidth(200);

        eventsTable.getColumns().clear();
        eventsTable.getColumns().addAll(nameColumn,dateColumn);

        eventsTable.getItems().clear();

        for(Eva event : eventsList.getEvents()){
            eventsTable.getItems().add(event);
//            System.out.println(event.getName()+" "+event.getDate());
        }
    }

}
