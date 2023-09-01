package cs211.project.controllers.login;

import cs211.project.model.Account;
import cs211.project.model.AccountList;
import cs211.project.services.AccountListFileDatasource;
import cs211.project.services.Datasource;
import cs211.project.services.FXRouter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TableView<Account> AccoutsTableView;
    private Datasource<AccountList> datasource;
    private AccountList accounts;

    @FXML
    private void onChangePasswordHyperlinkClick() throws IOException {
        FXRouter.goTo("changePassword");
    }

    @FXML
    private void BackButton() throws IOException {
        FXRouter.goTo("login");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datasource = new AccountListFileDatasource("data", "user-list.csv");
        accounts = datasource.readData();

        // Create columns
        TableColumn<Account, String> userColumn = new TableColumn<>("User");
        TableColumn<Account, String> passColumn = new TableColumn<>("Password");

        // Set cell value factories using getter methods
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        passColumn.setCellValueFactory(new PropertyValueFactory<>("pass"));

        userColumn.setMinWidth(300);
        passColumn.setMinWidth(300);
        // Add columns to TableView
        AccoutsTableView.getColumns().addAll(userColumn, passColumn);

        for (Account acc: accounts.getAdminList()) {
            AccoutsTableView.getItems().add(acc);
        }

        AccoutsTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                FXRouter.goTo("ban", newValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Use an ObservableList as the data source
        ObservableList<Account> accountObservableList = FXCollections.observableArrayList(accounts.getAdminList());

        // Set the items of the TableView to the ObservableList
        AccoutsTableView.setItems(accountObservableList);
    }
}